package com.wanted.lunchmapservice.restaurant.scheduler.mock;

import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.restaurant.entity.RawRestaurant;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.entity.id.RawRestaurantId;

public class FixtureData {

  public static RawRestaurant.RawRestaurantBuilder aRawRestaurant() {
    return RawRestaurant.builder()
        .rawRestaurantId(new RawRestaurantId("커피나무", "경기도 수원시 권선구 세류동 874-9"))
        .countryName("수원시").countryCode("").licenseDate("20110526")
        .businessStatus("영업").closureDate("").area("")
        .waterSupplyFacilityName("").maleWorkerNumber(0)
        .year(0).isMultiUseBusiness("").gradeName("")
        .totalFacilitySize("").femaleWorkerNumber(0)
        .businessPlaceSurroundingsName("주택가주변")
        .sanitaryBusinessDetailsName("")
        .sanitaryBusinessName("까페").totalWorkerNumber(0)
        .roadNameAddress("경기도 수원시 권선구 세지로 55 (세류동)").zipCode("16578")
        .longitude(127.0166894210).latitude(37.2570878627);
  }

  public static Restaurant.RestaurantBuilder aRestaurant() {
    return Restaurant.builder().location(aLocation().build()).name("커피나무")
        .lotNumberAddress("경기도 수원시 권선구 세류동 874-9")
        .roadNameAddress("경기도 수원시 권선구 세지로 55 (세류동)")
        .zipCode("16578")
        .longitude(127.0166894210).latitude(37.2570878627).averageScore(0.);
  }

  public static Location.LocationBuilder aLocation() {
    return Location.builder().cityName("경기").countryName("수원시")
        .longitude(127.0122222).latitude(37.30101111);
  }
}
