package com.wanted.lunchmapservice.openapi;

import com.wanted.lunchmapservice.openapi.properties.OpenApiProperties;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OpenApiUrls {

  private final OpenApiProperties apiProperties;
  private final List<String> apiUrlList = new ArrayList<>();

  public OpenApiUrls(OpenApiProperties apiProperties) {
    this.apiProperties = apiProperties;
  }

  public int getSize() {
    return apiProperties.getSize();
  }

  @PostConstruct
  public void init() {
    String params = getParameter();
    for (String url : apiProperties.getUrls()) {
      apiUrlList.add(url + params);
    }
  }

  private String getParameter() {
    StringBuilder builder = new StringBuilder();
    return builder.append("?key=").append(apiProperties.getKey())
        .append("&type=").append(apiProperties.getType())
        .append("&psize=").append(apiProperties.getSize()).toString();
  }
}
