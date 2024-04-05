package com.wanted.lunchmapservice.openapi.dto;

import com.wanted.lunchmapservice.restaurant.entity.RawRestaurant;
import com.wanted.lunchmapservice.restaurant.entity.id.RawRestaurantId;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@XmlRootElement(name = "OpenApiDto")
@Getter
@ToString
public class OpenApiDto {

  @XmlElement(name = "head")
  private ResponseHeader header;

  @XmlElement(name = "row")
  private List<ResponseData> dataList;

  public int getTotalCount() {
    return getHeader().getTotalCount();
  }

  @Getter
  @ToString
  static class ResponseHeader {

    @XmlElement(name = "list_total_count")
    private int totalCount;

    @XmlElement(name = "RESULT")
    private ResponseStatus status;

    @XmlElement(name = "api_version")
    private String apiVersion;
  }

  @Getter
  @ToString
  static class ResponseStatus {

    @XmlElement(name = "CODE")
    private String code;

    @XmlElement(name = "MESSAGE")
    private String message;
  }

  @Getter
  @ToString
  static class ResponseData {

    @XmlElement(name = "SIGUN_NM")
    private String countryName;

    @XmlElement(name = "SIGUN_CD")
    private String countryCode;

    @XmlElement(name = "BIZPLC_NM")
    private String name;

    @XmlElement(name = "LICENSG_DE")
    private String licenseDate;

    @XmlElement(name = "BSN_STATE_NM")
    private String businessStatus;

    @XmlElement(name = "CLSBIZ_DE")
    private String closureDate;

    @XmlElement(name = "LOCPLC_AR")
    private String area;

    @XmlElement(name = "GRAD_FACLT_DIV_NM")
    private String waterSupplyFacilityName;

    @XmlElement(name = "MALE_ENFLPSN_CNT")
    private Integer maleWorkerNumber;

    @XmlElement(name = "YY")
    private Integer year;

    @XmlElement(name = "MULTI_USE_BIZESTBL_YN")
    private String isMultiUseBusiness;

    @XmlElement(name = "GRAD_DIV_NM")
    private String gradeName;

    @XmlElement(name = "TOT_FACLT_SCALE")
    private String totalFacilitySize;

    @XmlElement(name = "FEMALE_ENFLPSN_CNT")
    private Integer femaleWorkerNumber;

    @XmlElement(name = "BSNSITE_CIRCUMFR_DIV_NM")
    private String businessPlaceSurroundingsName;

    @XmlElement(name = "SANITTN_INDUTYPE_NM")
    private String sanitaryBusinessDetailsName;

    @XmlElement(name = "SANITTN_BIZCOND_NM")
    private String sanitaryBusinessName;

    @XmlElement(name = "TOT_EMPLY_CNT")
    private Integer totalWorkerNumber;

    @XmlElement(name = "REFINE_LOTNO_ADDR")
    private String lotNumberAddress;

    @XmlElement(name = "REFINE_ROADNM_ADDR")
    private String roadNameAddress;

    @XmlElement(name = "REFINE_ZIP_CD")
    private String zipCode;

    @XmlElement(name = "REFINE_WGS84_LOGT")
    private Double longitude;

    @XmlElement(name = "REFINE_WGS84_LAT")
    private Double latitude;

    private boolean validate() {
      return StringUtils.hasText(name) && StringUtils.hasText(lotNumberAddress)
          && longitude != null && latitude != null;
    }

    private RawRestaurant toEntity() {
      return RawRestaurant.builder().rawRestaurantId(new RawRestaurantId(name, lotNumberAddress))
          .countryName(countryName).countryCode(countryCode).licenseDate(licenseDate)
          .businessStatus(businessStatus).closureDate(closureDate).area(area)
          .waterSupplyFacilityName(waterSupplyFacilityName).maleWorkerNumber(maleWorkerNumber)
          .year(year).isMultiUseBusiness(isMultiUseBusiness).gradeName(gradeName)
          .totalFacilitySize(totalFacilitySize).femaleWorkerNumber(femaleWorkerNumber)
          .businessPlaceSurroundingsName(businessPlaceSurroundingsName)
          .sanitaryBusinessDetailsName(sanitaryBusinessDetailsName)
          .sanitaryBusinessName(sanitaryBusinessName).totalWorkerNumber(totalWorkerNumber)
          .roadNameAddress(roadNameAddress).zipCode(zipCode)
          .longitude(longitude).latitude(latitude).build();
    }
  }

  public List<RawRestaurant> toEntity() {
    return dataList.stream()
        .filter(ResponseData::validate)
        .map(ResponseData::toEntity).toList();
  }
}

