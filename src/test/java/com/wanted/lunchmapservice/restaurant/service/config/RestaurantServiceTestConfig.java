package com.wanted.lunchmapservice.restaurant.service.config;

import com.wanted.lunchmapservice.restaurant.mapper.RestaurantMapper;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import com.wanted.lunchmapservice.restaurant.service.RestaurantGetService;
import com.wanted.lunchmapservice.restaurant.service.mock.RestaurantMock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RestaurantServiceTestConfig {

  @Bean
  public RestaurantRepository restaurantRepository() {
    return Mockito.mock(RestaurantRepository.class);
  }

  @Bean
  public RestaurantGetService restaurantGetService() {
    return new RestaurantGetService(restaurantRepository(), restaurantMapper());
  }

  @Bean
  public RestaurantMapper restaurantMapper() {
    return new RestaurantMapper();
  }

  @Bean
  public RestaurantMock restaurantMock() {
    return new RestaurantMock();
  }
}
