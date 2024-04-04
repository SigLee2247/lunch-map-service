package com.wanted.lunchmapservice.common.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.lunchmapservice.common.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtils {

    private final ObjectMapper objectMapper;


    public <T> T toEntity(HttpServletRequest request, Class<T> valueType) {
        try {
            return objectMapper.readValue(request.getInputStream(), valueType);
        } catch (IOException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity 변환 에러");
        }
    }

    public <T> T toEntity(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity 변환 에러");
        }
    }

    public String toStringValue(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 변환 에러");
        }
    }
}
