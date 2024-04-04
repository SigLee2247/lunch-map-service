package com.wanted.lunchmapservice.openapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.lunchmapservice.openapi.properties.OpenApiProperties;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(OpenApiUrls.class)
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = OpenApiProperties.class)
class OpenApiUrlsTest {

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  OpenApiUrls apiUrls;

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  OpenApiProperties apiProperties;

  @DisplayName("Open API 요청용 URL 테스트 : 성공")
  @Test
  void openApiUrlsTest() {
    List<String> urlList = apiUrls.getApiUrlList();

    assertThat(urlList).hasSize(apiProperties.getUrls().size())
        .allMatch(url -> url.contains("key"), "Open API key 파라미터 존재")
        .allMatch(url -> url.contains("type"), "Open API type 파라미터 존재")
        .allMatch(url -> url.contains("psize"), "Open API psize 파라미터 존재");
  }
}