package com.wanted.lunchmapservice.user.service;


import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.user.dto.request.UserPostRequestDto;
import com.wanted.lunchmapservice.user.dto.response.UserIdResponseDto;
import com.wanted.lunchmapservice.user.entity.User;
import com.wanted.lunchmapservice.user.mapper.UserMapper;
import com.wanted.lunchmapservice.user.repository.UserRepository;
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


    private void validUsernameExist(UserPostRequestDto post) {
        repository.findByUserName(post.getUserName()).ifPresent(data -> {
            throw new CommonException(
                    HttpStatus.CONFLICT, "사용할 수 없는 username 입니다.");
        });
    }
}
