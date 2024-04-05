package com.wanted.lunchmapservice.openapi.config;

import static org.mockito.BDDMockito.given;

import com.wanted.lunchmapservice.openapi.OpenApiCaller;
import com.wanted.lunchmapservice.openapi.OpenApiUrls;
import com.wanted.lunchmapservice.openapi.converter.XmlConverter;
import com.wanted.lunchmapservice.openapi.formatter.XmlFormatter;
import com.wanted.lunchmapservice.openapi.properties.OpenApiProperties;
import java.util.List;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OpenApiCallerTestConfig {

  @Bean
  public OpenApiCaller openApiCaller() {
    return new OpenApiCaller(openApiUrls(), xmlFormatter(), xmlConverter());
  }

  @Bean
  public OpenApiUrls openApiUrls() {
    return new OpenApiUrls(openApiProperties());
  }

  @Bean
  public OpenApiProperties openApiProperties() {
    OpenApiProperties apiProperties = Mockito.mock(OpenApiProperties.class);
    given(apiProperties.getUrls()).willReturn(List.of("http://localhost:8080"));
    given(apiProperties.getKey()).willReturn("abc123");
    given(apiProperties.getType()).willReturn("xml");
    given(apiProperties.getSize()).willReturn(3);
    return apiProperties;
  }

  @Bean
  public XmlFormatter xmlFormatter() {
    return Mockito.spy(XmlFormatter.class);
  }

  @Bean
  public XmlConverter xmlConverter() {
    return Mockito.spy(XmlConverter.class);
  }

  @Bean
  public List<String> mockXmlDataList() {
    return List.of(
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <Genrestrtcate>
            <head>
                <list_total_count>9</list_total_count>
                <RESULT>
                    <CODE>INFO-000</CODE>
                    <MESSAGE>정상 처리되었습니다.</MESSAGE>
                </RESULT>
                <api_version>1.0v</api_version>
            </head>
            <row></row>
            <row></row>
            <row></row>
        </Genrestrtcate>""",

        """
        <?xml version="1.0" encoding="UTF-8"?>
        <Genrestrtcate>
            <head>
                <list_total_count>9</list_total_count>
                <RESULT>
                    <CODE>INFO-000</CODE>
                    <MESSAGE>정상 처리되었습니다.</MESSAGE>
                </RESULT>
                <api_version>1.0v</api_version>
            </head>
            <row>
                <SIGUN_NM>수원시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>커피나무</BIZPLC_NM>
                <LICENSG_DE>20110526</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT></MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT>1</FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM>주택가주변</BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT></TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 수원시 권선구 세류동 874-9</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 수원시 권선구 세지로 55 (세류동)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>16578</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>127.0166894210</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.2570878627</REFINE_WGS84_LAT>
            </row>
            <row>
                <SIGUN_NM>파주시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>별빛카페 달빛차 3호</BIZPLC_NM>
                <LICENSG_DE>20141014</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT></MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT></FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM></BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT></TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 파주시 동패동 1920번지</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 파주시 동패로63번길 36-20, 1층 (동패동)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>10902</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>126.7381777919</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.7099931212</REFINE_WGS84_LAT>
            </row>
            <row>
                <SIGUN_NM>의정부시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>커티스에프앤비콤마</BIZPLC_NM>
                <LICENSG_DE>20140924</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT></MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT></FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM></BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT></TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 의정부시 금오동 454-3번지 지상1층</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 의정부시 금오로 108-10, 지상1층 (금오동)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>11755</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>127.0771013029</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.7562934791</REFINE_WGS84_LAT>
            </row>
        </Genrestrtcate>""",

        """
        <?xml version="1.0" encoding="UTF-8"?>
        <Genrestrtcate>
            <head>
                <list_total_count>9</list_total_count>
                <RESULT>
                    <CODE>INFO-000</CODE>
                    <MESSAGE>정상 처리되었습니다.</MESSAGE>
                </RESULT>
                <api_version>1.0v</api_version>
            </head>
            <row>
                <SIGUN_NM>용인시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>카페솔루션랩</BIZPLC_NM>
                <LICENSG_DE>20140415</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT>0</MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT>0</FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM></BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT>0</TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 용인시 수지구 신봉동 772-4 시골보신탕</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 용인시 수지구 신봉1로 107-7, 시골보신탕 1층 일부호 (신봉동)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>16814</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>127.0730307762</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.3246744734</REFINE_WGS84_LAT>
            </row>
            <row>
                <SIGUN_NM>파주시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>유정</BIZPLC_NM>
                <LICENSG_DE>20080625</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT>0</MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT>0</FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM></BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT>0</TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 파주시 금촌동 73-1 외1필지 1층</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 파주시 금정로 64, 1층 (금촌동)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>10929</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>126.7743533335</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.7603973789</REFINE_WGS84_LAT>
            </row>
            <row>
                <SIGUN_NM>하남시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>카페캐슬</BIZPLC_NM>
                <LICENSG_DE>19730921</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT>0</MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT>0</FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM>기타</BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT></TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 하남시 배알미동 172</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 하남시 미사대로 925 (배알미동)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>13029</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>127.2429528457</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.5410357996</REFINE_WGS84_LAT>
            </row>
        </Genrestrtcate>""",

        """
        <?xml version="1.0" encoding="UTF-8"?>
        <Genrestrtcate>
            <head>
                <list_total_count>9</list_total_count>
                <RESULT>
                    <CODE>INFO-000</CODE>
                    <MESSAGE>정상 처리되었습니다.</MESSAGE>
                </RESULT>
                <api_version>1.0v</api_version>
            </head>
            <row>
                <SIGUN_NM>의정부시</SIGUN_NM>
                <SIGUN_CD>41150</SIGUN_CD>
                <BIZPLC_NM>오크힐</BIZPLC_NM>
                <LICENSG_DE>2015-01-12</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT></MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT></FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM></BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT></TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 의정부시 장암동 101-9</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 의정부시 동일로150번길 114, 지상1.2층 (장암동)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>11724</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>127.0585251331</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.7053185486</REFINE_WGS84_LAT>
            </row>
            <row>
                <SIGUN_NM>용인시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>카파커피</BIZPLC_NM>
                <LICENSG_DE>20150130</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT></MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT></FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM></BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT></TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 용인시 처인구 유방동 1007-20번지 인정레포츠센타 123호</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 용인시 처인구 금어로 2, 1층 123호 (유방동, 인정레포츠센타)</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>17038</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>127.2155193387</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.2646643056</REFINE_WGS84_LAT>
            </row>
            <row>
                <SIGUN_NM>평택시</SIGUN_NM>
                <SIGUN_CD></SIGUN_CD>
                <BIZPLC_NM>와바송탄서정점</BIZPLC_NM>
                <LICENSG_DE>20060404</LICENSG_DE>
                <BSN_STATE_NM>영업</BSN_STATE_NM>
                <CLSBIZ_DE></CLSBIZ_DE>
                <LOCPLC_AR></LOCPLC_AR>
                <GRAD_FACLT_DIV_NM></GRAD_FACLT_DIV_NM>
                <MALE_ENFLPSN_CNT>0</MALE_ENFLPSN_CNT>
                <YY></YY>
                <MULTI_USE_BIZESTBL_YN></MULTI_USE_BIZESTBL_YN>
                <GRAD_DIV_NM></GRAD_DIV_NM>
                <TOT_FACLT_SCALE></TOT_FACLT_SCALE>
                <FEMALE_ENFLPSN_CNT>0</FEMALE_ENFLPSN_CNT>
                <BSNSITE_CIRCUMFR_DIV_NM>유흥업소밀집지역</BSNSITE_CIRCUMFR_DIV_NM>
                <SANITTN_INDUTYPE_NM></SANITTN_INDUTYPE_NM>
                <SANITTN_BIZCOND_NM>까페</SANITTN_BIZCOND_NM>
                <TOT_EMPLY_CNT></TOT_EMPLY_CNT>
                <REFINE_LOTNO_ADDR>경기도 평택시 서정동 795-6번지 (2층)</REFINE_LOTNO_ADDR>
                <REFINE_ROADNM_ADDR>경기도 평택시 특구로 28 (서정동,(2층))</REFINE_ROADNM_ADDR>
                <REFINE_ZIP_CD>17773</REFINE_ZIP_CD>
                <REFINE_WGS84_LOGT>127.0635280741</REFINE_WGS84_LOGT>
                <REFINE_WGS84_LAT>37.0680996712</REFINE_WGS84_LAT>
            </row>
        </Genrestrtcate>"""
    );
  }
}

