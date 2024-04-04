package com.wanted.lunchmapservice.location.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.location.service.LocationService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationCsvServiceTest {

  @InjectMocks
  LocationCsvInit locationCsvInit;

  @Mock
  LocationService locationService;

  @DisplayName("위치 정보 CSV 데이터와 동기화: 성공")
  @Test
  void storeLocationCsvDataTest() {
    //given
    Location location = Location.builder().cityName("강원").countryName("강릉시")
        .longitude(128.8784972).latitude(37.74913611).build();
    given(locationService.syncLocationData(anyList())).willReturn(List.of(location));
    given(locationService.getLocation(anyString(),anyString())).willReturn(location);


    //when
    locationCsvInit.storeLocationCsvData();

    //then
    then(locationService).should(times(1)).syncLocationData(anyList());

    assertThat(locationCsvInit.getLocation(location.getCode())).isEqualTo(location);
  }
}
