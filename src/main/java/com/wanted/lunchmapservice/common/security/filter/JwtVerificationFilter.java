package com.wanted.lunchmapservice.common.security.filter;

import com.wanted.lunchmapservice.common.security.enums.AuthExceptionCode;
import com.wanted.lunchmapservice.common.security.utils.JwtProperties;
import com.wanted.lunchmapservice.common.security.utils.JwtProvider;
import com.wanted.lunchmapservice.common.security.vo.Principal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            setAuthenticationToContext(request);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exceptionCode", AuthExceptionCode.ACCESS_TOKEN_EXPIRED);
        } catch (SignatureException se) {
            request.setAttribute("exceptionCode", AuthExceptionCode.INVALID_SIGNATURE_ACCESS_TOKEN);
        } catch (Exception e) {
            log.warn("Exception : {}, message : {}", e.getClass(), e.getMessage());
            request.setAttribute("exceptionCode", AuthExceptionCode.UNAUTHENTICATED);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !validAuthorizationHeader(request);
    }

    private String getAuthenticationTokenToHeader(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private boolean validAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = getAuthenticationTokenToHeader(request);
        return authorizationHeader != null && authorizationHeader.startsWith(
                jwtProperties.getPrefix());
    }

    private void setAuthenticationToContext(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(createAuthenticatedToken(request));
    }

    private Authentication createAuthenticatedToken(HttpServletRequest request) {
        Principal principal = createUserDetails(request);
        return new UsernamePasswordAuthenticationToken(principal.getId(), null,
                principal.getAuthorities());
    }

    private Principal createUserDetails(HttpServletRequest request) {
        String token = getAuthenticationTokenToHeader(request).substring(
                jwtProperties.getPrefixLength());
        return new Principal(jwtProvider.getClaims(token));
    }

}
