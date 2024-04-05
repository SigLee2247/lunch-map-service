package com.wanted.lunchmapservice.restaurant.repository.implement;

import com.wanted.lunchmapservice.common.config.QueryDslConfig;
import com.wanted.lunchmapservice.restaurant.controller.dto.NearRestaurantRequestDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import com.wanted.lunchmapservice.restaurant.repository.util.GeoLocationUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class})
class RestaurantRepositoryTest {

  final Double lat = 37.2738734428;
  final Double lon = 126.9412806228;
  final Double range = 4.0;
  @Autowired
  RestaurantRepository repository;
  NearRestaurantRequestDto dto;
  Pageable pageable;

  @BeforeEach
  void init() {

    dto = NearRestaurantRequestDto.builder()
        .currentLatitude(String.valueOf(lat))
        .currentLongitude(String.valueOf(lon))
        .range(range)
        .build();
    pageable = PageRequest.of(0, 100);
  }

  //
  @Test
  @DisplayName("성능 개선 전 코드와 현재 코드 중 현재 코드가 더 많거나 같은 양의 데이터를 가져야한다.")
  void findNearByRestaurantTest() {
    // given
    Page<Restaurant> resultByHaversine = repository.findNearByRestaurant(dto, pageable);
    Page<Restaurant> resultByEnhanced = repository.findNearByRestaurantV2(dto, pageable);

    Assertions.assertThat(resultByHaversine.getTotalElements()).isLessThanOrEqualTo(resultByEnhanced.getTotalElements());
  }

  @Test
  @DisplayName("인근지역 조회 레포지토리 테스트 : Haversine 공식 활용 시 상하 죄우의 좌표 값보다 작거나 같은 데이터를 가져야한다")
  void findNearByRestaurantHaversineTest() {
    // given
    Page<Restaurant> resultList = repository.findNearByRestaurant(dto, pageable);

    double diffLatitude = GeoLocationUtil.getDiffLatitude(range);
    double diffLongitude = GeoLocationUtil.getDiffLongitude(lat,range);

    for (Restaurant result : resultList) {
      Assertions.assertThat(result.getLatitude()).isLessThanOrEqualTo(lat + diffLatitude).isGreaterThanOrEqualTo(lat - diffLatitude);
      Assertions.assertThat(result.getLongitude()).isLessThanOrEqualTo(lon + diffLongitude).isGreaterThanOrEqualTo(lon - diffLongitude);
    }

  }

}