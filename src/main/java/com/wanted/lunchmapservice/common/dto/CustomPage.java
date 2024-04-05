package com.wanted.lunchmapservice.common.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class CustomPage<T> {
  private List<T> content;
  private PageInfo pageable;

  public CustomPage(List<T> content, PageInfo pageable) {
    this.content = content;
    this.pageable = pageable;
  }
}
