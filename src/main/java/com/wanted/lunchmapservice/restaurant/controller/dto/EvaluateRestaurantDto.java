package com.wanted.lunchmapservice.restaurant.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record EvaluateRestaurantDto(@PositiveOrZero @Max(value = 10) Integer score, String content) {

}
