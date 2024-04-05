package com.wanted.lunchmapservice.restaurant.controller.dto;

import lombok.Builder;

@Builder
public record ResponseLocationDto(String cityName,
                                  String countryName,
                                  Double longitude,
                                  Double latitude) {
}
