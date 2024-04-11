package com.wanted.lunchmapservice.restaurant.entity;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.wanted.lunchmapservice.rating.Rating;
import com.wanted.lunchmapservice.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestaurantTest {

  Restaurant restaurant;
  Rating ratingOne;
  Rating ratingTwo;

  @BeforeEach
  void init() {
    restaurant = Restaurant.builder()
        .id(1L)
        .averageScore(0D)
        .build();
    ratingOne = Rating.of(4, "", User.of(1L));
    ratingTwo = Rating.of(5, "", User.of(1L));
  }

  @Test
  @DisplayName("rate 추가 시 데이터가 정상적으로 변경이 가능한지 여부 체크 : 초기 데이터가 0 일 때")
  void addRatingTest() {
    restaurant.addRating(ratingOne);
    Assertions.assertThat(restaurant.getAverageScore()).isEqualTo(4D);

    restaurant.addRating(ratingTwo); //4 + 5 / 2 => 9 / 2 -> 4.5
    Assertions.assertThat(restaurant.getAverageScore()).isEqualTo(4.5D);

    restaurant.addRating(ratingOne); //4 + 5 + 4 / 3=> 13 / 3 -> 4.333333 -> 반올림 실패 -> 4.3
    Assertions.assertThat(restaurant.getAverageScore()).isEqualTo(4.3D);
  }

}