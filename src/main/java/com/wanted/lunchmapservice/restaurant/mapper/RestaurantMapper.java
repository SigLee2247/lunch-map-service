package com.wanted.lunchmapservice.restaurant.mapper;

import com.wanted.lunchmapservice.common.dto.CustomPage;
import com.wanted.lunchmapservice.common.dto.PageInfo;
import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.rating.Rating;
import com.wanted.lunchmapservice.restaurant.controller.dto.GetRestaurantDetailResponseDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.GetRestaurantSimpleResponseDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.LocationDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.RatingDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import java.util.List;
import org.springframework.data.domain.Page;
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


  public ResponseDto<CustomPage<GetRestaurantSimpleResponseDto>> toResponseDto(
      Page<Restaurant> entityPage) {
    return ResponseDto.<CustomPage<GetRestaurantSimpleResponseDto>>builder()
        .data(toCustomPageDto(entityPage))
        .code(HttpStatus.OK.value())
        .message(HttpStatus.OK.getReasonPhrase())
        .build();
  }

  private CustomPage<GetRestaurantSimpleResponseDto> toCustomPageDto(Page<Restaurant> entityPage) {
    return new CustomPage<>(toGetListDto(entityPage.getContent()),
        new PageInfo(entityPage.getPageable().getOffset(), entityPage.getSize(),
            entityPage.getTotalElements(), entityPage.isFirst(),
            entityPage.getNumberOfElements(), entityPage.isFirst(),
            entityPage.getTotalPages()));

  }

  private List<GetRestaurantSimpleResponseDto> toGetListDto(List<Restaurant> entityList) {
    return entityList.stream().map(this::toGetSimpleDto).toList();
  }

  private GetRestaurantSimpleResponseDto toGetSimpleDto(Restaurant entity) {
    return GetRestaurantSimpleResponseDto.builder()
        .restaurantId(entity.getId())
        .restaurantName(entity.getName())
        .lotNumberAddress(entity.getLotNumberAddress())
        .roadNameAddress(entity.getRoadNameAddress())
        .longitude(entity.getLongitude())
        .latitude(entity.getLatitude())
        .location(toDto(entity.getLocation()))
        .averageScore(entity.getAverageScore())
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
        .ratingList(toDtoList(entity.getRatingList()))
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

  private List<RatingDto> toDtoList(List<Rating> entityList) {
    return entityList.stream().map(this::toDto).toList();
  }

  private RatingDto toDto(Rating entity) {
    return RatingDto.builder()
        .content(entity.getContent())
        .username(entity.getUser().getUserName())
        .score(entity.getScore())
        .build();
  }
}