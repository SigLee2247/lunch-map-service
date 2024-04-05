package com.wanted.lunchmapservice.restaurant.repository;

import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
  @Query("SELECT DISTINCT r FROM Restaurant r " +
      "LEFT JOIN FETCH r.ratingList rr " +
      "LEFT JOIN FETCH rr.user " +
      "WHERE r.id = :id")
  Optional<Restaurant> findById(@Param("id") Long id);
}
