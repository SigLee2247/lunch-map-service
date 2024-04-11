package com.wanted.lunchmapservice.restaurant.controller.config;

import com.wanted.lunchmapservice.restaurant.service.RestaurantGetService;
import com.wanted.lunchmapservice.restaurant.service.mock.RestaurantMock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RestaurantControllerTestConfig extends AuthTestConfig{

  @Bean
  public RestaurantGetService restaurantGetService() {
    return Mockito.mock(RestaurantGetService.class);
  }

  @Bean
  public RestaurantMock restaurantMock() {
    return new RestaurantMock();
  }
}

