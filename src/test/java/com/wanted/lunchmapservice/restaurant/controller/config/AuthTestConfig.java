package com.wanted.lunchmapservice.restaurant.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.lunchmapservice.common.redis.repository.RedisRepository;
import com.wanted.lunchmapservice.common.security.handler.AuthenticationEntryPointHandler;
import com.wanted.lunchmapservice.common.security.handler.AuthenticationFailureCustomHandler;
import com.wanted.lunchmapservice.common.security.handler.LogoutSuccessCustomHandler;
import com.wanted.lunchmapservice.common.security.service.AuthService;
import com.wanted.lunchmapservice.common.security.service.UserDetailsServiceImpl;
import com.wanted.lunchmapservice.common.security.utils.JwtProperties;
import com.wanted.lunchmapservice.common.security.utils.JwtProvider;
import com.wanted.lunchmapservice.common.security.utils.ObjectMapperUtils;
import com.wanted.lunchmapservice.restaurant.controller.mock.AuthMock;
import com.wanted.lunchmapservice.user.repository.UserRepository;
import com.wanted.lunchmapservice.user.service.UserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class AuthTestConfig {

  @Bean
  public JwtProvider jwtProvider() {
    return new JwtProvider(jwtProperties());
  }

  @Bean
  public JwtProperties jwtProperties() {
    return new JwtProperties();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public AuthenticationFailureCustomHandler authenticationFailureCustomHandler() {
    return new AuthenticationFailureCustomHandler(objectMapperUtils());
  }

  @Bean
  public AuthenticationEntryPointHandler authenticationEntryPointHandler() {
    return new AuthenticationEntryPointHandler(objectMapperUtils());
  }

  @Bean
  public LogoutSuccessCustomHandler logoutSuccessCustomHandler() {
    return new LogoutSuccessCustomHandler(redisRepository());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(userRepository());
  }

  @Bean
  public AuthService authService() {
    return new AuthService(redisRepository(), jwtProvider(),jwtProperties(),userRepository(), objectMapperUtils());
  }

  @Bean
  public ObjectMapperUtils objectMapperUtils() {
    return new ObjectMapperUtils(objectMapper());
  }

  @Bean
  public UserService userService() {
    return Mockito.mock(UserService.class);
  }

  @Bean
  public UserRepository userRepository() {
    return Mockito.mock(UserRepository.class);
  }

  @Bean
  public RedisRepository redisRepository() {
    return Mockito.mock(RedisRepository.class);
  }

  @Bean
  public AuthMock authMock() {
    return new AuthMock(jwtProvider());
  }
}
