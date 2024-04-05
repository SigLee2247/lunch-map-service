package com.wanted.lunchmapservice.restaurant.repository.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class GeoLocationUtil {
  private static final Integer EARTH_RADIUS = 6371;
  private static final Double DEVIATION = 3.0;
  public static double getDiffLongitude(double lat, double range){
    return ((1 / (EARTH_RADIUS * 1 * (Math.PI / 180) * Math.cos(Math.toRadians(lat))))) * getRange(range);
  }
  public static double getDiffLatitude(double range){
    return ((1 / (EARTH_RADIUS * 1 * (Math.PI / 180)))) * getRange(range);
  }

  private static double getRange(double range){
    return (range / 2) + DEVIATION;
  }
}
