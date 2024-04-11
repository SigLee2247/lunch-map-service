package com.wanted.lunchmapservice.restaurant.controller.mock;


import com.wanted.lunchmapservice.common.security.utils.JwtProvider;
import com.wanted.lunchmapservice.common.security.vo.Principal;
import com.wanted.lunchmapservice.user.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthMock {
  private JwtProvider provider;

  public String createAccessToken() {
    return "Bearer " + provider.generateAccessToken("user", 1L, "ROLE_USER");
  }

  public Principal creatUserDetails() {
    return new Principal(createUserEntity(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
  }

  private User createUserEntity() {
    User user = User.builder()
        .id(1L)
        .userName("test001")
        .password("p1@wqqedwada")
        .build();
    return user;
  }

  @Autowired
  public AuthMock(JwtProvider provider) {
    this.provider = provider;
  }
}

