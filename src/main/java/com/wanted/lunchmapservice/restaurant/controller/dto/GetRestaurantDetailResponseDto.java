package com.wanted.lunchmapservice.restaurant.controller.dto;

import lombok.Builder;

@Builder
public record GetRestaurantDetailResponseDto(Long restaurantId, String restaurantName,
                                             String lotNumberAddress, String roadNameAddress,
                                             String zipCode, Double longitude,
                                             Double latitude, LocationDto location,
                                             Double averageScore) {

}

