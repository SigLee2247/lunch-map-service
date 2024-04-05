package com.wanted.lunchmapservice.restaurant.service;


import com.wanted.lunchmapservice.common.dto.CustomPage;
import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantDetailDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.ResponseGetRestaurantSimpleDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.mapper.RestaurantMapper;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import com.wanted.lunchmapservice.restaurant.repository.dto.RequestRestaurantGetFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantGetService {
  private final RestaurantRepository repository;
  private final RestaurantMapper mapper;
  public ResponseDto<ResponseGetRestaurantDetailDto> getDetails(Long restaurantId) {
    return mapper.toResponseDto(validRestaurant(restaurantId));
  }

  public ResponseDto<CustomPage<ResponseGetRestaurantSimpleDto>> getRestaurant(
      RequestRestaurantGetFilterDto request, Pageable pageable) {
    return mapper.toResponseDto(repository.findPageByFilter(pageable,request));
  }

  private Restaurant validRestaurant(Long restaurantId) {
    return repository.findByIdFetch(restaurantId)
        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "맛집 정보가 존재하지 않습니다."));
  }
}

