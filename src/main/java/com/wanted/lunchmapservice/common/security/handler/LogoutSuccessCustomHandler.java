package com.wanted.lunchmapservice.common.security.handler;

import com.wanted.lunchmapservice.common.redis.repository.RedisRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutSuccessCustomHandler implements LogoutSuccessHandler {

    private final RedisRepository repository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        Cookie[] cookies = validCookiesExist(request);

        deleteCookie(searchCookieProperties(cookies));
        deleteTokenToRedis(searchCookieProperties(cookies));

        createResponse(response);
    }

    private void deleteTokenToRedis(Cookie refreshCookie) {
        String refreshToken = refreshCookie.getValue();
        repository.delete(refreshToken);
    }

    private Cookie[] validCookiesExist(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{});
    }

    private Cookie searchCookieProperties(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("Refresh"))
                .findFirst()
                .orElse(new Cookie("Refresh", ""));
    }

    private void deleteCookie(Cookie cookie) {
        cookie.setMaxAge(0);
    }

    private static void createResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().flush();
    }

}
