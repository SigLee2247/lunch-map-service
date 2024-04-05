package com.wanted.lunchmapservice.restaurant.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantGetFilterDto {
  private String cityName;
  private String countryName;
}

