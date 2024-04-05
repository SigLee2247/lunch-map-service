package com.wanted.lunchmapservice.openapi.converter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.lunchmapservice.common.exception.CommonException;
import com.wanted.lunchmapservice.openapi.dto.OpenApiDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(XmlConverter.class)
@ExtendWith(SpringExtension.class)
class XmlConverterTest {

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  XmlConverter xmlConverter;

  @DisplayName("xml 데이터 컨버터 테스트 : 성공")
  @Test
  void xmlConverterTest() {
    //given
    int listTotalCount = 4752;
    String xmlData = String.format("""
            <OpenApiDto>
                <head>
                    <list_total_count>%d</list_total_count>
                    <RESULT>
                        <CODE>INFO-000</CODE>
                        <MESSAGE>정상 처리되었습니다.</MESSAGE>
                    </RESULT>
                    <api_version>1.0v</api_version>
                </head>
                <row>
                    <SIGUN_NM>수원시</SIGUN_NM>
                    <SIGUN_CD/>
                    <BIZPLC_NM>커피나무</BIZPLC_NM>
                    <LICENSG_DE>20110526</LICENSG_DE>
                    <BSN_STATE_NM>영업</BSN_STATE_NM>
                    <CLSBIZ_DE/>
                    <LOCPLC_AR/>
                    <GRAD_FACLT_DIV_NM/>
                    <MALE_ENFLPSN_CNT/>
                    <YY/>
                    <MULTI_USE_BIZESTBL_YN/>
                    <GRAD_DIV_NM/>
                    <TOT_FACLT_SCALE/>
                    <FEMALE_ENFLPSN_CNT>1</FEMALE_ENFLPSN_CNT>
                    <BSNSITE_CIRCUMFR_DIV_NM>주택가주변</BSNSITE_CIRCUMFR_DIV_NM>
                    <SANITTN_INDUTYPE_NM/>
                    <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                    <TOT_EMPLY_CNT/>
                    <REFINE_LOTNO_ADDR>경기도 수원시 권선구 세류동 874-9</REFINE_LOTNO_ADDR>
                    <REFINE_ROADNM_ADDR>경기도 수원시 권선구 세지로 55 (세류동)</REFINE_ROADNM_ADDR>
                    <REFINE_ZIP_CD>16578</REFINE_ZIP_CD>
                    <REFINE_WGS84_LOGT>127.0166894210</REFINE_WGS84_LOGT>
                    <REFINE_WGS84_LAT>37.2570878627</REFINE_WGS84_LAT>
                </row>
            </OpenApiDto>
            """, listTotalCount);

    //when
    OpenApiDto openApiDto = xmlConverter.convert(xmlData);

    //then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(openApiDto.getTotalCount()).isEqualTo(listTotalCount);
    softAssertions.assertThat(openApiDto.getDataList().size()).isEqualTo(1);
    softAssertions.assertAll();
  }

  @DisplayName("xml 데이터 컨버터 테스트 : 실패")
  @Test
  void xmlConverterTestFail() {
    //given
    String xmlData = """
            <Genrestrtcate>
                <head></head>
                <row></row>
            </Genrestrtcate>
            """;

    //when, then
    assertThatThrownBy(() -> xmlConverter.convert(xmlData))
        .isInstanceOf(CommonException.class)
        .extracting("httpStatus", "message")
        .containsExactly(HttpStatus.INTERNAL_SERVER_ERROR, "XML 형식이 올바르지 않습니다.");
  }
}