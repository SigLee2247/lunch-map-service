package com.wanted.lunchmapservice.restaurant.controller;

import com.wanted.lunchmapservice.common.dto.CustomPage;
import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.common.security.annotation.CurrentUser;
import com.wanted.lunchmapservice.restaurant.controller.dto.EvaluateRestaurantDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantDetailDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantSimpleDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.RestaurantResponseDto;
import com.wanted.lunchmapservice.restaurant.repository.dto.RequestRestaurantGetFilterDto;
import com.wanted.lunchmapservice.restaurant.service.RestaurantGetService;
import com.wanted.lunchmapservice.restaurant.controller.dto.NearRestaurantRequestDto;
import com.wanted.lunchmapservice.restaurant.service.RestaurantService;
import com.wanted.lunchmapservice.user.controller.dto.RestaurantIdResponseDto;
import com.wanted.lunchmapservice.user.utils.UriCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
  private static final String URL = "/api/restaurants";
  private final RestaurantGetService getService;
  private final RestaurantService service;

  @GetMapping("/{restaurantId}")
  public ResponseEntity<ResponseDto<ResponseGetRestaurantDetailDto>> getRestaurantDetail(
      @PathVariable Long restaurantId) {
    return ResponseEntity.ok(getService.getDetails(restaurantId));
  }

  @GetMapping
  public ResponseEntity<ResponseDto<CustomPage<ResponseGetRestaurantSimpleDto>>> getRestaurant(
      @ModelAttribute RequestRestaurantGetFilterDto request, Pageable pageable) {
    return ResponseEntity.ok(getService.getRestaurant(request, pageable));
  }

  @GetMapping("/nearby")
  public ResponseEntity<ResponseDto<CustomPage<RestaurantResponseDto>>> getRecommendedRestaurant(
      @ModelAttribute
      NearRestaurantRequestDto dto, Pageable pageable) {
    return ResponseEntity.ok(getService.findNearbyRestaurant(dto, pageable));
  }

  @PostMapping("/evaluation/{restaurantId}")
  public ResponseEntity<ResponseDto<RestaurantIdResponseDto>> evaluateRestaurant(
      @PathVariable @PositiveOrZero Long restaurantId,
      @Valid @RequestBody EvaluateRestaurantDto dto, @CurrentUser Long userId) {
    var result = service.evaluateRestaurant(userId, restaurantId, dto);
    URI location = UriCreator.createUri(URL, result.getData().restaurantId());
    return ResponseEntity.ok().location(location).body(result);
  }
}

