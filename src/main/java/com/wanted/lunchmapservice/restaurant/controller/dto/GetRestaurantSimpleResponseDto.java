package com.wanted.lunchmapservice.restaurant.controller.dto;

import lombok.Builder;

@Builder
public record GetRestaurantSimpleResponseDto(Long restaurantId, String restaurantName,
                                             String lotNumberAddress, String roadNameAddress,
                                             Double longitude, Double latitude, LocationDto location,
                                             Double averageScore) {

}