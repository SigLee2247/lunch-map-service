package com.wanted.lunchmapservice.restaurant.mapper;

import com.wanted.lunchmapservice.common.dto.CustomPage;
import com.wanted.lunchmapservice.common.dto.PageInfo;
import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.rating.Rating;
import com.wanted.lunchmapservice.restaurant.controller.dto.EvaluateRestaurantDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantDetailDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantSimpleDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseLocationDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseRatingDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.RestaurantResponseDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.user.controller.dto.RestaurantIdResponseDto;
import com.wanted.lunchmapservice.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

  public ResponseDto<ResponseGetRestaurantDetailDto> toResponseDto(Restaurant entity) {
    return ResponseDto.<ResponseGetRestaurantDetailDto>builder()
        .data(toDto(entity))
        .message(HttpStatus.OK.getReasonPhrase())
        .code(HttpStatus.OK.value())
        .build();
  }

  public ResponseDto<RestaurantIdResponseDto> toIdResponseDto(Restaurant entity) {
    return ResponseDto.<RestaurantIdResponseDto>builder()
        .data(toIdDto(entity))
        .message(HttpStatus.OK.getReasonPhrase())
        .code(HttpStatus.OK.value())
        .build();
  }

  public Rating toEntity(Long userId, EvaluateRestaurantDto dto) {
    return Rating.of(dto.score(), dto.content(), User.of(userId));
  }

  public ResponseDto<CustomPage<ResponseGetRestaurantSimpleDto>> toResponseDto(
      Page<Restaurant> entityPage) {
    return ResponseDto.<CustomPage<ResponseGetRestaurantSimpleDto>>builder()
        .data(toCustomPageDto(entityPage))
        .code(HttpStatus.OK.value())
        .message(HttpStatus.OK.getReasonPhrase())
        .build();
  }

  public ResponseDto<CustomPage<RestaurantResponseDto>> toResponseNearDto(
      Page<Restaurant> entityPage) {
    return ResponseDto.<CustomPage<RestaurantResponseDto>>builder()
        .data(toNearCustomPageDto(entityPage))
        .code(HttpStatus.OK.value())
        .message(HttpStatus.OK.getReasonPhrase())
        .build();
  }

  public RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant) {
    return RestaurantResponseDto.builder()
        .id(restaurant.getId())
        .locationId(restaurant.getLocation().getId())
        .name(restaurant.getName())
        .lotNumberAddress(restaurant.getLotNumberAddress())
        .roadNameAddress(restaurant.getRoadNameAddress())
        .zipCode(restaurant.getZipCode())
        .latitude(restaurant.getLatitude())
        .longitude(restaurant.getLongitude())
        .averageScore(restaurant.getAverageScore())
        .build();
  }

  private CustomPage<RestaurantResponseDto> toNearCustomPageDto(
      Page<Restaurant> entityPage) {
    return new CustomPage<>(toGetNearListDto(entityPage.getContent()),
        new PageInfo(entityPage.getPageable().getOffset(), entityPage.getSize(),
            entityPage.getTotalElements(), entityPage.isFirst(),
            entityPage.getNumberOfElements(), entityPage.isFirst(),
            entityPage.getTotalPages()));
  }

  private CustomPage<ResponseGetRestaurantSimpleDto> toCustomPageDto(Page<Restaurant> entityPage) {
    return new CustomPage<>(toGetListDto(entityPage.getContent()),
        new PageInfo(entityPage.getPageable().getOffset(), entityPage.getSize(),
            entityPage.getTotalElements(), entityPage.isFirst(),
            entityPage.getNumberOfElements(), entityPage.isFirst(),
            entityPage.getTotalPages()));

  }

  private List<ResponseGetRestaurantSimpleDto> toGetListDto(List<Restaurant> entityList) {
    return entityList.stream().map(this::toGetSimpleDto).toList();
  }

  private List<RestaurantResponseDto> toGetNearListDto(List<Restaurant> entityList) {
    return entityList.stream().map(this::toRestaurantResponseDto).toList();
  }

  private ResponseGetRestaurantSimpleDto toGetSimpleDto(Restaurant entity) {
    return ResponseGetRestaurantSimpleDto.builder()
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

  private ResponseGetRestaurantDetailDto toDto(Restaurant entity) {
    return ResponseGetRestaurantDetailDto.builder()
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

  private ResponseLocationDto toDto(Location entity) {
    return ResponseLocationDto.builder()
        .cityName(entity.getCityName())
        .countryName(entity.getCountryName())
        .longitude(entity.getLongitude())
        .latitude(entity.getLatitude())
        .build();
  }

  private List<ResponseRatingDto> toDtoList(List<Rating> entityList) {
    return entityList.stream().map(this::toDto).toList();
  }

  private ResponseRatingDto toDto(Rating entity) {
    return ResponseRatingDto.builder()
        .content(entity.getContent())
        .username(entity.getUser().getUserName())
        .score(entity.getScore())
        .build();
  }

  private RestaurantIdResponseDto toIdDto(Restaurant entity) {
    return RestaurantIdResponseDto.builder().restaurantId(entity.getId()).build();
  }
}