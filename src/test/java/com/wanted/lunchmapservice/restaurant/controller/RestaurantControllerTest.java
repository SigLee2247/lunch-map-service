package com.wanted.lunchmapservice.restaurant.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.lunchmapservice.common.config.SecurityConfig;
import com.wanted.lunchmapservice.common.security.service.UserDetailsServiceImpl;
import com.wanted.lunchmapservice.common.security.vo.Principal;
import com.wanted.lunchmapservice.restaurant.controller.config.RestaurantControllerTestConfig;
import com.wanted.lunchmapservice.restaurant.controller.mock.AuthMock;
import com.wanted.lunchmapservice.restaurant.service.RestaurantGetService;
import com.wanted.lunchmapservice.restaurant.service.mock.RestaurantMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(RestaurantController.class)
@Import({RestaurantControllerTestConfig.class, SecurityConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
class RestaurantControllerTest {

  private final String DEFAULT_URL = "/api/restaurants";
  @Autowired
  MockMvc mvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  RestaurantMock mock;
  @Autowired
  AuthMock authMock;
  @MockBean
  RestaurantGetService getService;
  @MockBean
  UserDetailsServiceImpl detailsService;
  String accessToken;
  Principal principal;

  @BeforeEach
  void init() {
    accessToken = authMock.createAccessToken();
    principal = authMock.creatUserDetails();
  }

  @Test
  @DisplayName("맛집 상세 조회 서비스 테스트")
  void get_restaurant_detail_success_test() throws Exception {
    // given
    Long requestId = 1L;

    BDDMockito.given(getService.getDetails(anyLong()))
        .willReturn(mock.getDetailResponse(requestId));
    BDDMockito.given(detailsService.loadUserByUsername(anyString())).willReturn(principal);
    // when
    ResultActions perform = mvc.perform(
        MockMvcRequestBuilders.get(DEFAULT_URL + "/{restaurantId}", requestId).header(
            HttpHeaders.AUTHORIZATION, accessToken));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isMap())
        .andExpect(jsonPath("$.data.restaurantId").isNumber())
        .andExpect(jsonPath("$.data.restaurantName").isString())
        .andExpect(jsonPath("$.data.roadNameAddress").isString())
        .andExpect(jsonPath("$.data.zipCode").isString())
        .andExpect(jsonPath("$.data.averageScore").isNumber())
        .andExpect(jsonPath("$.data.location").isMap())
        .andExpect(jsonPath("$.data.location.countryName").isString())
        .andExpect(jsonPath("$.data.location.longitude").isNumber())
        .andExpect(jsonPath("$.data.location.latitude").isNumber())
        .andExpect(jsonPath("$.data.location.cityName").isString());
  }

  @Test
  @DisplayName("맛집 상세 조회 api 테스트 : 실패 [비로그인]")
  void get_restaurant_detail_fail_test() throws Exception {
    // given
    Long requestId = 1L;

    BDDMockito.given(detailsService.loadUserByUsername(anyString())).willReturn(principal);
    // when
    ResultActions perform = mvc.perform(
        MockMvcRequestBuilders.get(DEFAULT_URL + "/{restaurantId}", requestId));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

}