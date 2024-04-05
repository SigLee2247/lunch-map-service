package com.wanted.lunchmapservice.restaurant.scheduler.mock;

import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RestaurantMock {

  private String name = "아도니스";
  private String lotNumberAddress = "경기도 가평군 상면 행현리 602-3번지";
  private String roadNameAddress = "경기도 가평군 상면 수목원로 314-2";
  private String zipCode = "12448";
  private Double longitude = 37.7516678333;
  private Double latitude = 127.3588076752;
  private Double averageScore = 0.0;
  private String cityName = "경기도";
  private String countryName = "가평군";

  public Restaurant getEntity(Long id) {
    return Restaurant.builder()
        .id(id)
        .location(createLocation())
        .name(name)
        .lotNumberAddress(lotNumberAddress)
        .roadNameAddress(roadNameAddress)
        .zipCode(zipCode)
        .longitude(longitude)
        .latitude(latitude)
        .averageScore(averageScore)
        .build();
  }

  private Location createLocation() {
    return Location.builder()
        .id(1L)
        .cityName(cityName)
        .countryName(countryName)
        .longitude(longitude)
        .latitude(latitude)
        .build();
  }
}
