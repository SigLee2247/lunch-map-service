package com.wanted.lunchmapservice.user.service;


import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.user.controller.dto.UserPatchDto;
import com.wanted.lunchmapservice.user.dto.request.UserPostRequestDto;
import com.wanted.lunchmapservice.user.dto.response.UserIdResponseDto;
import com.wanted.lunchmapservice.user.entity.User;
import com.wanted.lunchmapservice.user.mapper.UserMapper;
import com.wanted.lunchmapservice.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public ResponseDto<UserIdResponseDto> saveUser(UserPostRequestDto postDto) {
        validUsernameExist(postDto);
        postDto.setPassword(encoder.encode(postDto.getPassword()));

        User requestEntity = mapper.toEntity(postDto);

        User save = repository.save(requestEntity);
        return mapper.toIdResponseDto(save);
    }

    public ResponseDto<UserIdResponseDto> updateUser(Long userId, UserPatchDto dto) {
        User findUser = validUserExist(userId);
        updateUserData(dto, findUser);

        return mapper.toIdResponseDto(findUser);
    }

    private static void updateUserData(UserPatchDto dto, User findUser) {
        Optional.ofNullable(dto.lat()).ifPresent(findUser::changeLat);
        Optional.ofNullable(dto.lon()).ifPresent(findUser::changeLon);
        Optional.ofNullable(dto.serviceAccess()).ifPresent(findUser::changeServiceAccess);
        Optional.ofNullable(dto.username()).ifPresent(findUser::changeUsername);
    }


    private void validUsernameExist(UserPostRequestDto post) {
        repository.findByUserName(post.getUserName()).ifPresent(data -> {
            throw new CommonException(
                    HttpStatus.CONFLICT, "사용할 수 없는 username 입니다.");
        });
    }
    private User validUserExist(Long userId){
        return repository.findById(userId)
            .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "회원을 조회할 수 없습니다."));
    }
}
