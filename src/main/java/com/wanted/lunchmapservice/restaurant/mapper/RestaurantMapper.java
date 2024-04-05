package com.wanted.lunchmapservice.restaurant.mapper;

import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.restaurant.controller.dto.GetRestaurantDetailResponseDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.LocationDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

  public ResponseDto<GetRestaurantDetailResponseDto> toResponseDto(Restaurant entity) {
    return ResponseDto.<GetRestaurantDetailResponseDto>builder()
        .data(toDto(entity))
        .message(HttpStatus.OK.getReasonPhrase())
        .code(HttpStatus.OK.value())
        .build();
  }

  private GetRestaurantDetailResponseDto toDto(Restaurant entity) {
    return GetRestaurantDetailResponseDto.builder()
        .restaurantId(entity.getId())
        .averageScore(entity.getAverageScore())
        .restaurantName(entity.getName())
        .latitude(entity.getLatitude())
        .longitude(entity.getLongitude())
        .lotNumberAddress(entity.getLotNumberAddress())
        .roadNameAddress(entity.getRoadNameAddress())
        .location(toDto(entity.getLocation()))
        .zipCode(entity.getZipCode())
        .build();
  }

  private LocationDto toDto(Location entity) {
    return LocationDto.builder()
        .cityName(entity.getCityName())
        .countryName(entity.getCountryName())
        .longitude(entity.getLongitude())
        .latitude(entity.getLatitude())
        .build();
  }

}
