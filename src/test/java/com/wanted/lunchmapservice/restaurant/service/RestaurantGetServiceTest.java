package com.wanted.lunchmapservice.restaurant.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantRepository;
import com.wanted.lunchmapservice.restaurant.service.config.RestaurantServiceTestConfig;
import com.wanted.lunchmapservice.restaurant.service.mock.RestaurantMock;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(RestaurantServiceTestConfig.class)
class RestaurantGetServiceTest {

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  RestaurantGetService service;
  @MockBean
  RestaurantRepository repository;
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  RestaurantMock mock;

  @Test
  @DisplayName("맛집 상세 조회 : 성공")
  void get_restaurant_detail_success_test() throws Exception {
    // given
    long requestId = 1L;
    Restaurant afterRepository = mock.getEntity(requestId);

    given(repository.findById(anyLong())).willReturn(Optional.of(afterRepository));
    // when
    var result = service.getDetails(requestId);
    // then
    assertThat(result.getData().restaurantId()).isEqualTo(requestId);
    assertThat(result.getData().averageScore()).isEqualTo(mock.getAverageScore());
    assertThat(result.getData().restaurantName()).isEqualTo(mock.getName());
    assertThat(result.getData().roadNameAddress()).isEqualTo(mock.getRoadNameAddress());
    assertThat(result.getData().lotNumberAddress()).isEqualTo(mock.getLotNumberAddress());
    assertThat(result.getData().zipCode()).isEqualTo(mock.getZipCode());
    assertThat(result.getData().longitude()).isEqualTo(mock.getLongitude());
    assertThat(result.getData().latitude()).isEqualTo(mock.getLatitude());
    assertThat(result.getData().location().latitude()).isEqualTo(mock.getLatitude());
    assertThat(result.getData().location().longitude()).isEqualTo(mock.getLongitude());
    assertThat(result.getData().location().cityName()).isEqualTo(mock.getCityName());
    assertThat(result.getData().location().countryName()).isEqualTo(mock.getCountryName());
  }

  @Test
  @DisplayName("맛집 상세 조회 : 실패[조회불가]")
  void get_restaurant_detail_fail_test() {
    // given
    long requestId = 1L;
    given(repository.findById(anyLong())).willReturn(Optional.empty());
    // when
    // then
    assertThatThrownBy(() -> service.getDetails(requestId))
        .isInstanceOf(CommonException.class)
        .hasMessage("맛집 정보가 존재하지 않습니다.");
  }
}