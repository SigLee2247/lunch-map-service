package com.wanted.lunchmapservice.user.mapper;


import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.user.dto.request.UserPostRequestDto;
import com.wanted.lunchmapservice.user.dto.response.UserIdResponseDto;
import com.wanted.lunchmapservice.user.entity.User;
import com.wanted.lunchmapservice.user.service.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User toEntity(UserPostRequestDto post) {
        return User.builder()
                .userName(post.getUserName())
                .password(post.getPassword())
                .build();
    }

    public ResponseDto<UserIdResponseDto> toIdResponseDto(User user) {
        return ResponseDto.<UserIdResponseDto>builder()
                .code(HttpStatus.CREATED.value())
                .data(toIdResponse(user))
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

    public ResponseDto<UserGetDto> toResponseDto(User entity) {
        return ResponseDto.<UserGetDto>builder()
            .data(toGetDto(entity))
            .message(HttpStatus.OK.getReasonPhrase())
            .build();
    }

    private UserIdResponseDto toIdResponse(User user) {
        return new UserIdResponseDto(user.getId());
    }

    public UserGetDto toGetDto(User user) {
        return UserGetDto.builder()
            .userId(user.getId())
            .name(user.getUserName())
            .lat(user.getLatitude())
            .lon(user.getLongitude())
            .build();
    }
}
