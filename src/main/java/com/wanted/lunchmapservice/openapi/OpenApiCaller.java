package com.wanted.lunchmapservice.openapi;

import com.wanted.lunchmapservice.openapi.converter.XmlConverter;
import com.wanted.lunchmapservice.openapi.dto.OpenApiDto;
import com.wanted.lunchmapservice.openapi.formatter.XmlFormatter;
import com.wanted.lunchmapservice.restaurant.entity.RawRestaurant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OpenApiCaller {

  private final OpenApiUrls apiUrls;
  private final XmlFormatter formatter;
  private final XmlConverter converter;

  public List<RawRestaurant> callApi() {
    RestTemplate restTemplate = new RestTemplate();
    List<RawRestaurant> rawDataList = new ArrayList<>();
    for (String url : apiUrls.getApiUrlList()) {
      callApi(restTemplate, rawDataList, url);
    }
    return rawDataList;
  }

  private void callApi(RestTemplate restTemplate, List<RawRestaurant> rawDataList, String url) {
    int totalCount = getRawData(restTemplate, url).getTotalCount();
    int page = 1;
    int size = apiUrls.getSize();

    while (hasNextPage(totalCount, page, size)) {
      String pageParam = "&pindex=" + (page++);
      OpenApiDto rawData = getRawData(restTemplate, url + pageParam);
      rawDataList.addAll(rawData.toEntity());
    }
  }

  private OpenApiDto getRawData(RestTemplate restTemplate, String url) {
    String data = restTemplate.getForObject(url, String.class);
    String formattedData = formatter.format(data);
    return converter.convert(formattedData);
  }

  private static boolean hasNextPage(int totalCount, int page, int size) {
    return (page - 1) * size < totalCount;
  }
}
