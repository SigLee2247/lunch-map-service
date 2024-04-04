package com.wanted.lunchmapservice.user.mapper;


import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.user.dto.request.UserPostRequestDto;
import com.wanted.lunchmapservice.user.dto.response.UserIdResponseDto;
import com.wanted.lunchmapservice.user.entity.User;
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

    private UserIdResponseDto toIdResponse(User user) {
        return new UserIdResponseDto(user.getId());
    }
}
