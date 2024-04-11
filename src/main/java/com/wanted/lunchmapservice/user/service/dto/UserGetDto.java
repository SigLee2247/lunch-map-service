package com.wanted.lunchmapservice.user.service.dto;

import lombok.Builder;

@Builder
public record UserGetDto(Long userId, String name, Double lat, Double lon) {

}
