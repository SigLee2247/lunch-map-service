package com.wanted.lunchmapservice.openapi.formatter;

import com.wanted.lunchmapservice.openapi.dto.OpenApiDto;
import org.springframework.stereotype.Component;

@Component
public class XmlFormatter {

  private static final char END_OF_OPEN_TAG = '>';
  private static final char START_OF_CLOSED_TAG = '<';
  private static final String OPEN_TAG = "<" + OpenApiDto.class.getSimpleName() + ">";
  private static final String CLOSED_TAG = "</" + OpenApiDto.class.getSimpleName() + ">";
  private static final String XML_DECLARATION_TAG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  public String format(String xmlData) {
    int beginIndex = xmlData.indexOf(END_OF_OPEN_TAG, XML_DECLARATION_TAG.length()) + 1;
    int endIndex = xmlData.lastIndexOf(START_OF_CLOSED_TAG);
    String subXmlData = xmlData.substring(beginIndex, endIndex);
    return OPEN_TAG + subXmlData + CLOSED_TAG;
  }
}
