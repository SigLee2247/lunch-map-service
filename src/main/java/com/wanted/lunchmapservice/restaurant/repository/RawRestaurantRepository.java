package com.wanted.lunchmapservice.restaurant.repository;

import com.wanted.lunchmapservice.restaurant.entity.RawRestaurant;
import com.wanted.lunchmapservice.restaurant.entity.id.RawRestaurantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawRestaurantRepository extends JpaRepository<RawRestaurant, RawRestaurantId> {

}
