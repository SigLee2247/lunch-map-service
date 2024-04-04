package com.wanted.lunchmapservice.openApi.properties;

import com.wanted.lunchmapservice.common.config.property.YamlPropertySourceFactory;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("open-api")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class OpenApiProperties {

  private List<String> urls;
  private String key;
  private String type;
  private Integer size;
}
