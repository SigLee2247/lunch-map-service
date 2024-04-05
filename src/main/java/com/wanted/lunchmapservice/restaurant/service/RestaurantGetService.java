package com.wanted.lunchmapservice.restaurant.service;


import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.restaurant.controller.dto.GetRestaurantDetailResponseDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.mapper.RestaurantMapper;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantGetService {
  private final RestaurantRepository repository;
  private final RestaurantMapper mapper;
  public ResponseDto<GetRestaurantDetailResponseDto> getDetails(Long restaurantId) {
    return mapper.toResponseDto(validRestaurant(restaurantId));
  }

  private Restaurant validRestaurant(Long restaurantId) {
    return repository.findById(restaurantId)
        .orElseThrow(() -> new CommonException(HttpStatus.CONFLICT, "맛집 정보가 존재하지 않습니다."));
  }
}

