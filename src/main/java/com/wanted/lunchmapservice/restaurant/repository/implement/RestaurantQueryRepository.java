package com.wanted.lunchmapservice.restaurant.repository.implement;

import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.dto.RestaurantGetFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantQueryRepository {
  Page<Restaurant> findPageByFilter(Pageable pageable, RestaurantGetFilterDto condition);
}