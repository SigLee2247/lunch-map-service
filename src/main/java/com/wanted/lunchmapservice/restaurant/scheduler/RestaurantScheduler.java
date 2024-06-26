package com.wanted.lunchmapservice.restaurant.scheduler;

import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.location.utils.LocationCsvInit;
import com.wanted.lunchmapservice.openapi.OpenApiCaller;
import com.wanted.lunchmapservice.restaurant.entity.RawRestaurant;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.RawRestaurantRepository;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestaurantScheduler {

  private final OpenApiCaller apiCaller;
  private final LocationCsvInit locationCsvInit;
  private final RawRestaurantRepository rawRestaurantRepository;
  private final RestaurantRepository restaurantRepository;

  @Transactional
  @Scheduled(initialDelay = 30000, fixedDelay = 3600000) //서버 시작 30초 후, 이후 1시간 마다
  public void syncRestaurantData() {
    rawRestaurantRepository.findAll(); //영속성 컨텍스트 영속화
    List<RawRestaurant> rawRestaurantList = apiCaller.callApi();
    rawRestaurantRepository.saveAll(rawRestaurantList); //update or insert
    syncRestaurantData(rawRestaurantList);
  }

  private void syncRestaurantData(List<RawRestaurant> rawRestaurantList) {
    List<Restaurant> restaurantList = restaurantRepository.findAll();
    for (RawRestaurant rawRestaurant : rawRestaurantList) {
      restaurantList.stream()
          .filter(restaurant -> restaurant.isSame(rawRestaurant))
          .findFirst()
          .ifPresentOrElse(restaurant -> updateData(rawRestaurant, restaurant),
              () -> insertData(rawRestaurant));
    }
  }

  private void updateData(RawRestaurant rawRestaurant, Restaurant restaurant) {
    Location location = locationCsvInit.getLocation(rawRestaurant.getLocationCode());
    restaurant.update(location, rawRestaurant);
  }

  private void insertData(RawRestaurant rawRestaurant) {
    Location location = locationCsvInit.getLocation(rawRestaurant.getLocationCode());
    Restaurant restaurant = Restaurant.of(location, rawRestaurant);
    restaurantRepository.save(restaurant);
  }
}
