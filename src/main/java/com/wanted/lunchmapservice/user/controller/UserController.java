package com.wanted.lunchmapservice.user.controller;

import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.user.controller.dto.UserPatchDto;
import com.wanted.lunchmapservice.user.dto.request.UserPostRequestDto;
import com.wanted.lunchmapservice.user.dto.response.UserIdResponseDto;
import com.wanted.lunchmapservice.user.service.UserGetService;
import com.wanted.lunchmapservice.user.service.UserService;
import com.wanted.lunchmapservice.user.service.dto.UserGetDto;
import com.wanted.lunchmapservice.user.utils.UriCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private static final String URL = "/api/users";
  private final UserService service;
  private final UserGetService getService;

  @PostMapping
  public ResponseEntity<ResponseDto<UserIdResponseDto>> postUser(
      @RequestBody @Valid UserPostRequestDto post) {
    ResponseDto<UserIdResponseDto> result = service.saveUser(post);
    URI location = UriCreator.createUri(URL, result.getData().getUserId());
    return ResponseEntity.created(location).body(result);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<ResponseDto<UserGetDto>> getUser(@PathVariable @Positive Long userId) {
    return ResponseEntity.ok(getService.getUserInfo(userId));
  }

  @PatchMapping("{userId}")
  public ResponseEntity<ResponseDto<UserIdResponseDto>> patchUser(@PathVariable Long userId,
      @RequestBody UserPatchDto dto) {
    return ResponseEntity.ok(service.updateUser(userId, dto));
  }
}
