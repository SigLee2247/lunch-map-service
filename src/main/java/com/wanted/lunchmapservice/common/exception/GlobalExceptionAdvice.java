package com.wanted.lunchmapservice.common.exception;

import com.wanted.lunchmapservice.common.security.exception.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBusinessException(CommonException e) {
        log.error("BusinessLogic Exception Error : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(e.getHttpStatus());
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    /*
     * Dto 검증오류
     * */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {}", e.getMessage());
        return ErrorResponse.of(e.getBindingResult());
    }

    /*
     * Controller 검증 오류
     * */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException : {}", e.getMessage());
        return ErrorResponse.of(e.getConstraintViolations());
    }
}