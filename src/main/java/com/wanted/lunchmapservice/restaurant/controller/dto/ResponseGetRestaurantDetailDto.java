package com.wanted.lunchmapservice.restaurant.controller.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ResponseGetRestaurantDetailDto(Long restaurantId, String restaurantName,
                                             String lotNumberAddress, String roadNameAddress,
                                             String zipCode, Double longitude,
                                             Double latitude, ResponseLocationDto location,
                                             Double averageScore,
                                             List<ResponseRatingDto> ratingList) {

}
