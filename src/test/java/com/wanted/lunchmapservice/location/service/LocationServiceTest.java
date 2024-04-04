package com.wanted.lunchmapservice.location.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.location.repository.LocationRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

  @InjectMocks
  LocationService locationService;

  @Mock
  LocationRepository locationRepository;

  @DisplayName("위치 정보 CSV 데이터와 동기화 테스트 : 성공")
  @Test
  void syncLocationDataTest() {
    //given
    Location originLocation = Location.builder().cityName("강원").countryName("강릉시")
        .longitude(128.8).latitude(37.7).build();
    Location updatedLocation = Location.builder().cityName("강원").countryName("강릉시")
        .longitude(128.8784972).latitude(37.74913611).build();
    Location newLocation = Location.builder().cityName("강원").countryName("고성군")
        .longitude(128.4701639).latitude(38.37796111).build();
    List<Location> originLocationList = new ArrayList<>(List.of(originLocation));
    List<Location> csvLocationList = new ArrayList<>(List.of(updatedLocation, newLocation));
    given(locationRepository.findAll()).willReturn(originLocationList);

    //when
    List<Location> newLocationList = locationService.syncLocationData(csvLocationList);

    //then
    then(locationRepository).should(times(1)).findAll();
    then(locationRepository).should(times(1)).saveAll(anyList());

    assertThat(newLocationList).hasSize(2);
    assertThat(originLocation).extracting("longitude", "latitude")
        .containsExactly(updatedLocation.getLongitude(), updatedLocation.getLatitude());
    assertThat(newLocationList.get(1)).isEqualTo(newLocation);
  }
}
