package com.wanted.lunchmapservice.restaurant.scheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.wanted.lunchmapservice.location.utils.LocationCsvInit;
import com.wanted.lunchmapservice.openapi.OpenApiCaller;
import com.wanted.lunchmapservice.restaurant.entity.RawRestaurant;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.entity.id.RawRestaurantId;
import com.wanted.lunchmapservice.restaurant.repository.RawRestaurantRepository;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import com.wanted.lunchmapservice.restaurant.scheduler.mock.FixtureData;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantSchedulerServiceTest {

  @InjectMocks
  RestaurantScheduler restaurantScheduler;
  @Mock
  OpenApiCaller openApiCaller;
  @Mock
  LocationCsvInit locationCsvInit;
  @Mock
  RawRestaurantRepository rawRestaurantRepository;
  @Mock
  RestaurantRepository restaurantRepository;
  @Captor
  ArgumentCaptor<Restaurant> restaurantArgumentCaptor;

  @DisplayName("식당 정보 외부 API와 동기화 테스트 : 성공")
  @Test
  void syncRestaurantData() {
    //given
    RawRestaurant updatedRawRestaurant = FixtureData.aRawRestaurant().zipCode("12345").build();
    RawRestaurant newRawRestaurant = FixtureData.aRawRestaurant()
        .rawRestaurantId(new RawRestaurantId("카페솔루션랩", "경기도 용인시 수지구 신봉동 772-4 시골보신탕")).build();
    Restaurant restaurant = FixtureData.aRestaurant().zipCode("54321").build();
    List<RawRestaurant> rawRestaurantList = List.of(updatedRawRestaurant, newRawRestaurant);
    List<Restaurant> restaurantList = List.of(restaurant);
    given(openApiCaller.callApi()).willReturn(rawRestaurantList);
    given(restaurantRepository.findAll()).willReturn(restaurantList);

    //when
    restaurantScheduler.syncRestaurantData();

    //then
    then(rawRestaurantRepository).should(times(1)).findAll();
    then(rawRestaurantRepository).should(times(1)).saveAll(rawRestaurantList);
    then(restaurantRepository).should(times(1)).findAll();
    then(restaurantRepository).should(times(1)).save(restaurantArgumentCaptor.capture());
    assertThat(restaurant.getZipCode()).isEqualTo(updatedRawRestaurant.getZipCode());
    assertThat(restaurantArgumentCaptor.getValue())
        .extracting("name", "lotNumberAddress")
        .containsExactly("카페솔루션랩", "경기도 용인시 수지구 신봉동 772-4 시골보신탕");
  }
}