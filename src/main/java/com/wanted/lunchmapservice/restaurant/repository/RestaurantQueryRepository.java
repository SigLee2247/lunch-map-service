package com.wanted.lunchmapservice.restaurant.repository;

import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.dto.RequestRestaurantGetFilterDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.NearRestaurantRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantQueryRepository {
  Page<Restaurant> findPageByFilter(Pageable pageable, RequestRestaurantGetFilterDto condition);

  Page<Restaurant> findNearByRestaurant(NearRestaurantRequestDto dto, Pageable pageable);

}