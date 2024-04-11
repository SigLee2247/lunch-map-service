package com.wanted.lunchmapservice.location.utils.dto;

import com.opencsv.bean.CsvBindByPosition;
import com.wanted.lunchmapservice.location.entity.Location;
import lombok.Getter;

@Getter
public class LocationCsvDto {

  @CsvBindByPosition(position = 0)
  private String cityName;

  @CsvBindByPosition(position = 1)
  private String countryName;

  @CsvBindByPosition(position = 2)
  private String longitude;

  @CsvBindByPosition(position = 3)
  private String latitude;

  public Location toEntity() {
    return Location.builder()
        .cityName(cityName)
        .countryName(countryName)
        .longitude(Double.valueOf(longitude))
        .latitude(Double.valueOf(latitude)).build();
  }
}
