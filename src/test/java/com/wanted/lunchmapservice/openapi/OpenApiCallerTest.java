package com.wanted.lunchmapservice.openapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.wanted.lunchmapservice.openapi.config.OpenApiCallerTestConfig;
import com.wanted.lunchmapservice.openapi.converter.XmlConverter;
import com.wanted.lunchmapservice.openapi.formatter.XmlFormatter;
import com.wanted.lunchmapservice.restaurant.entity.RawRestaurant;
import java.io.IOException;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(OpenApiCallerTestConfig.class)
@ExtendWith(SpringExtension.class)
class OpenApiCallerTest {

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  OpenApiCaller openApiCaller;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  XmlFormatter xmlFormatter;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  XmlConverter xmlConverter;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  List<String> mockXmlDataList;

  static MockWebServer mockWebServer;

  @BeforeAll
  static void initAll() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start(8080);
  }

  @AfterAll
  static void tearDownAll() throws IOException {
    mockWebServer.shutdown();
  }

  @DisplayName("Open API 응답 결과 엔티티로 매핑 테스트 : 성공")
  @Test
  void openApiCallerTest() {
    //given
    for (String xmlData : mockXmlDataList) {
      mockWebServer.enqueue(new MockResponse()
          .setBody(xmlData)
          .setHeader("Content-Type", "application/xml;charset=UTF-8"));
    }

    //when
    List<RawRestaurant> rawRestaurantList = openApiCaller.callApi();

    //then
    then(xmlFormatter).should(times(4)).format(anyString());
    then(xmlConverter).should(times(4)).convert(anyString());
    assertThat(rawRestaurantList).hasSize(9);
  }
}
