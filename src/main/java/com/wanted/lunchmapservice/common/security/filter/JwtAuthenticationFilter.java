package com.wanted.lunchmapservice.common.security.filter;

import com.wanted.lunchmapservice.common.redis.repository.RedisRepository;
import com.wanted.lunchmapservice.common.security.dto.LoginDto;
import com.wanted.lunchmapservice.common.security.dto.UserInfo;
import com.wanted.lunchmapservice.common.security.utils.JwtProperties;
import com.wanted.lunchmapservice.common.security.utils.JwtProvider;
import com.wanted.lunchmapservice.common.security.utils.ObjectMapperUtils;
import com.wanted.lunchmapservice.common.security.vo.Principal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapperUtils objectMapper;
    private final RedisRepository repository;
    private final JwtProperties jwtProperties;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        LoginDto requestData = objectMapper.toEntity(request, LoginDto.class);
        var authenticationToken = createAuthenticationToken(requestData);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) {
        Principal principal = (Principal) authResult.getPrincipal();

        String accessToken =
                jwtProvider.generateAccessToken(principal.getUsername(), principal.getId(),
                        toTrans(principal.getAuthorities()));
        String refreshToken = jwtProvider.generateRefreshToken(principal.getUsername());

        repository.save(refreshToken, objectMapper.toStringValue(createUserInfo(principal)),
                jwtProperties.getRefreshTokenValidityInSeconds());
        response.setHeader(HttpHeaders.AUTHORIZATION,
                jwtProperties.getPrefix() + " " + accessToken);
        response.addCookie(createCookie(refreshToken));
    }

    private UserInfo createUserInfo(Principal principal) {
        return new UserInfo(principal.getUsername(), toTrans(principal.getAuthorities()));
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(LoginDto login) {
        return new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword());
    }

    private Cookie createCookie(String key) {
        Cookie cookie = new Cookie("Refresh", key);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/reissue");
        cookie.setMaxAge(60 * jwtProperties.getRefreshTokenValidityInSeconds());
        return cookie;
    }

    private String toTrans(Collection<GrantedAuthority> list) {
        return StringUtils.collectionToCommaDelimitedString(list);
    }

}
