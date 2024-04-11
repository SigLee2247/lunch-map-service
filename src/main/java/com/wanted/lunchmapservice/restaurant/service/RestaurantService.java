package com.wanted.lunchmapservice.restaurant.service;

import com.wanted.lunchmapservice.common.dto.ResponseDto;
import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.rating.Rating;
import com.wanted.lunchmapservice.restaurant.controller.dto.EvaluateRestaurantDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.mapper.RestaurantMapper;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import com.wanted.lunchmapservice.user.controller.dto.RestaurantIdResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {
  private final RestaurantRepository repository;
  private final RestaurantMapper mapper;
  public ResponseDto<RestaurantIdResponseDto> evaluateRestaurant(Long userId, Long restaurantId, EvaluateRestaurantDto dto) {
    Restaurant findRestaurant = validRestaurantExist(restaurantId);
    Rating entity = mapper.toEntity(userId, dto);
    findRestaurant.addRating(entity);

    return mapper.toIdResponseDto(findRestaurant);
  }

  private Restaurant validRestaurantExist(Long restaurantId) {
    return repository.findByIdFetchRating(restaurantId)
        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "음식점을 찾을 수 없습니다."));
  }
}
