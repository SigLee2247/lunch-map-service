package com.wanted.lunchmapservice.restaurant.repository.implement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.wanted.lunchmapservice.common.config.QueryDslConfig;
import com.wanted.lunchmapservice.restaurant.controller.dto.NearRestaurantRequestDto;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.util.StopWatch;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class})
class RestaurantRepositoryTest {

  final Double lat = 37.2738734428;
  final Double lon = 126.9412806228;
  final Double range = 10.0;
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
  @DisplayName("인근지역 조회 레포지토리 테스트")
  void findNearByRestaurantTest() {
    // given
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Page<Restaurant> nearByRestaurant = repository.findNearByRestaurant(dto, pageable);
    stopWatch.stop();

    GeoLocation location = new GeoLocation(lat, lon, range);

    for (Restaurant result : nearByRestaurant) {
      Assertions.assertThat(result.getLongitude()).isGreaterThan(location.getMinLon())
          .isLessThan(location.getMaxLon());
      Assertions.assertThat(result.getLatitude()).isGreaterThan(location.getMinLat())
          .isLessThan(location.getMaxLat());

    }
  }

  @Getter
  class GeoLocation {

    private static final Integer EARTH_RADIUS = 6371;
    double minLat;
    double maxLat;
    double minLon;
    double maxLon;

    double diffLat;
    double diffLon;

    public GeoLocation(double lat, double lon, double range) {
      initData(lat, lon, range);
      this.minLat = lat - diffLat;
      this.maxLat = lat + diffLat;
      this.minLon = lon - diffLon;
      this.maxLon = lon + diffLon;
    }

    //각 계산 방식의 오차 산정
    private void initData(double lat, double lon, double range) {
      range = (range / 2) + 5;
      //1M 이동 했을 때 걸리는 위도
      diffLat = ((1 / (EARTH_RADIUS * 1 * (Math.PI / 180)))) * range;
      //m당 x 좌표 이동 값
      diffLon =
          ((1 / (EARTH_RADIUS * 1 * (Math.PI / 180) * Math.cos(Math.toRadians(lat))))) * range;
    }
  }
}