package com.wanted.lunchmapservice.restaurant.controller;

import com.wanted.lunchmapservice.common.dto.CustomPage;
import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantDetailDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantSimpleDto;
import com.wanted.lunchmapservice.restaurant.repository.dto.RequestRestaurantGetFilterDto;
import com.wanted.lunchmapservice.restaurant.service.RestaurantGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
  private final RestaurantGetService getService;

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

}

