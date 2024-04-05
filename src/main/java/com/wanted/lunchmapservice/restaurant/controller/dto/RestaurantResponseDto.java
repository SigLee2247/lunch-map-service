package com.wanted.lunchmapservice.restaurant.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantResponseDto {

  private Long id;
  private Long locationId;
  private String name;
  private String lotNumberAddress;
  private String roadNameAddress;
  private String zipCode;
  private Double longitude;
  private Double latitude;
  private Double averageScore;

}