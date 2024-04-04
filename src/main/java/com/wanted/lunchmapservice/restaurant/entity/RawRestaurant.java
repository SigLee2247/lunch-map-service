package com.wanted.lunchmapservice.restaurant.entity;


import com.wanted.lunchmapservice.common.BaseTime;
import com.wanted.lunchmapservice.restaurant.entity.id.RawRestaurantId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RawRestaurant extends BaseTime {

  @EmbeddedId
  private RawRestaurantId rawRestaurantId;

  @ColumnDefault("'EMPTY'")
  @Column(name = "country_name", nullable = false)
  private String countryName;

  @ColumnDefault("'EMPTY'")
  @Column(name = "country_code", nullable = false)
  private String countryCode;

  @ColumnDefault("'EMPTY'")
  @Column(name = "license_date", nullable = false)
  private String licenseDate;

  @ColumnDefault("'EMPTY'")
  @Column(name = "business_status", nullable = false)
  private String businessStatus;

  @ColumnDefault("'EMPTY'")
  @Column(name = "closure_date", nullable = false)
  private String closureDate;

  @ColumnDefault("'EMPTY'")
  @Column(name = "area", nullable = false)
  private String area;

  @ColumnDefault("'EMPTY'")
  @Column(name = "water_supply_facility_name", nullable = false)
  private String waterSupplyFacilityName;

  @ColumnDefault("0")
  @Column(name = "male_worker_number", nullable = false)
  private Integer maleWorkerNumber;

  @ColumnDefault("0")
  @Column(name = "year", nullable = false)
  private Integer year;

  @ColumnDefault("'EMPTY'")
  @Column(name = "is_multi_use_business", nullable = false)
  private String isMultiUseBusiness;

  @ColumnDefault("'EMPTY'")
  @Column(name = "grade_name", nullable = false)
  private String gradeName;

  @ColumnDefault("'EMPTY'")
  @Column(name = "total_facility_size", nullable = false)
  private String totalFacilitySize;

  @ColumnDefault("0")
  @Column(name = "female_worker_number", nullable = false)
  private Integer femaleWorkerNumber;

  @ColumnDefault("'EMPTY'")
  @Column(name = "business_place_surroundings_name", nullable = false)
  private String businessPlaceSurroundingsName;

  @ColumnDefault("'EMPTY'")
  @Column(name = "sanitary_business_details_name", nullable = false)
  private String sanitaryBusinessDetailsName;

  @ColumnDefault("'EMPTY'")
  @Column(name = "sanitary_business_name", nullable = false)
  private String sanitaryBusinessName;

  @ColumnDefault("0")
  @Column(name = "total_worker_number", nullable = false)
  private Integer totalWorkerNumber;

  @ColumnDefault("'EMPTY'")
  @Column(name = "road_name_address", nullable = false)
  private String roadNameAddress;

  @ColumnDefault("'EMPTY'")
  @Column(name = "zip_code", nullable = false)
  private String zipCode;

  @ColumnDefault("0")
  @Column(name = "longitude", nullable = false)
  private Double longitude;

  @ColumnDefault("0")
  @Column(name = "latitude", nullable = false)
  private Double latitude;

  public String getName() {
    return rawRestaurantId.getName();
  }

  public String getLotNumberAddress() {
    return rawRestaurantId.getLotNumberAddress();
  }

  //Restaurant 엔티티 저장 시 필요한 LocationCode 반환을 위한 getter
  public String getLocationCode() {
    return getLotNumberAddress().substring(0, 2) + " " + countryName;
  }
}
