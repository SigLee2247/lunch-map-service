package com.wanted.lunchmapservice.user.service;

import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.user.entity.User;
import com.wanted.lunchmapservice.user.mapper.UserMapper;
import com.wanted.lunchmapservice.user.repository.UserRepository;
import com.wanted.lunchmapservice.user.service.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserGetService {
  private final UserRepository repository;
  private final UserMapper mapper;

  public ResponseDto<UserGetDto> getUserInfo(Long userId) {
    return mapper.toResponseDto(validUserExist(userId));
  }

  private User validUserExist(Long userId) {
    return repository.findById(userId)
        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "회원을 조회할 수 없습니다."));
  }
}
