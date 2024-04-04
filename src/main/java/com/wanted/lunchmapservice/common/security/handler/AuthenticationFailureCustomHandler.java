package com.wanted.lunchmapservice.common.security.handler;

import com.wanted.lunchmapservice.common.security.enums.AuthExceptionCode;
import com.wanted.lunchmapservice.common.security.exception.ErrorResponse;
import com.wanted.lunchmapservice.common.security.utils.ObjectMapperUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureCustomHandler implements AuthenticationFailureHandler {

    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.error("# Authentication failed: {}", exception.getMessage());
        log.error("authentication ", exception);
        sendErrorResponse(response);
    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        setResponseHeader(response);
        response.getWriter().write(getResponseData());
    }

    private String getResponseData() {
        return objectMapperUtils.toStringValue(createErrorResponse());
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");
    }

    private ErrorResponse createErrorResponse() {
        return ErrorResponse.of(AuthExceptionCode.UNAUTHENTICATED.getHttpStatus(),
                AuthExceptionCode.UNAUTHENTICATED.getMessage());
    }
}
