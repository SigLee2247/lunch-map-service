package com.wanted.lunchmapservice.common.security.service;

import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.common.redis.repository.RedisRepository;
import com.wanted.lunchmapservice.common.security.dto.UserInfo;
import com.wanted.lunchmapservice.common.security.enums.AuthExceptionCode;
import com.wanted.lunchmapservice.common.security.utils.JwtProperties;
import com.wanted.lunchmapservice.common.security.utils.JwtProvider;
import com.wanted.lunchmapservice.common.security.utils.ObjectMapperUtils;
import com.wanted.lunchmapservice.user.entity.User;
import com.wanted.lunchmapservice.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisRepository redis;
    private final JwtProvider provider;
    private final JwtProperties properties;
    private final UserRepository userRepository;
    private final ObjectMapperUtils objectMapperUtils;

    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        UserInfo info = findUserInfo(findCookie(request));
        User findUser = validRefreshTokenSubject(info);

        String refresh = getRefreshToken(findUser);
        String access = getAccessToken(findUser, info);

        saveUserInfoToRedis(refresh, info);

        response.setHeader(HttpHeaders.AUTHORIZATION, access);
        response.addCookie(createCookie(refresh));
    }

    private String getRefreshToken(User findUser) {
        return provider.generateRefreshToken(findUser.getUserName());
    }

    private String getAccessToken(User findUser, UserInfo info) {
        return properties.getPrefix() + provider.generateAccessToken(findUser.getUserName(),
                findUser.getId(), info.getAuthorities());
    }

    private void saveUserInfoToRedis(String refresh, UserInfo info) {
        redis.save(refresh, objectMapperUtils.toStringValue(info), provider.getRefreshTokenValidityInSeconds());
    }

    private User validRefreshTokenSubject(UserInfo userInfo) {
        return userRepository.findByUserName(userInfo.getUserName())
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"));
    }

    private Cookie findCookie(HttpServletRequest request) {
        return searchCookieProperties(validCookiesExist(request));
    }

    private UserInfo findUserInfo(Cookie refreshCookie) {
        return objectMapperUtils.toEntity(findAndDeleteToRedis(refreshCookie), UserInfo.class);
    }

    private String findAndDeleteToRedis(Cookie refreshCookie) {
        String tokenToRedis = findTokenToRedis(refreshCookie);
        deleteToken(tokenToRedis);
        return tokenToRedis;
    }

    private void deleteToken(String tokenToRedis) {
        redis.delete(tokenToRedis);
    }

    private String findTokenToRedis(Cookie refreshCookie) {
        return Optional.ofNullable(redis.findByKey(refreshCookie.getValue()))
                .orElseThrow(() -> new CommonException(
                        AuthExceptionCode.REFRESH_TOKEN_NOT_FOUND.getHttpStatus(),
                        AuthExceptionCode.REFRESH_TOKEN_NOT_FOUND.getMessage()));
    }

    private Cookie[] validCookiesExist(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{});
    }

    private Cookie searchCookieProperties(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("Refresh"))
                .findFirst()
                .orElseThrow(() -> new CommonException(
                        AuthExceptionCode.REFRESH_TOKEN_EXPIRED.getHttpStatus(),
                        AuthExceptionCode.REFRESH_TOKEN_EXPIRED.getMessage()));
    }

    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie("Refresh", value);
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(provider.getRefreshTokenValidityInSeconds() * 60);
        cookie.setPath("/api/auth/reissue");
        cookie.setSecure(true);
        return cookie;
    }
}
