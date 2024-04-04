package com.wanted.lunchmapservice.restaurant.entity;

import com.wanted.lunchmapservice.restaurant.entity.id.RawRestaurantId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Entity
public class Restaurant {

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

    @ColumnDefault("-1")
    @Column(name = "area", nullable = false)
    private Double area;

    @ColumnDefault("'EMPTY'")
    @Column(name = "water_supply_facility_name", nullable = false)
    private String waterSupplyFacilityName;

    @ColumnDefault("-1")
    @Column(name = "male_worker_number", nullable = false)
    private Integer maleWorkerNum;

    @ColumnDefault("-1")
    @Column(name = "year", nullable = false)
    private Integer year;

    @ColumnDefault("'EMPTY'")
    @Column(name = "is_multi_use_business", nullable = false)
    private String multiUseBusiness;

    @ColumnDefault("'EMPTY'")
    @Column(name = "grade_name", nullable = false)
    private String gradeName;

    @ColumnDefault("'EMPTY'")
    @Column(name = "total_facility_size", nullable = false)
    private String totalFacilitySize;

    @ColumnDefault("-1")
    @Column(name = "female_worker_number", nullable = false)
    private Integer femaleWorkerNum;

    @ColumnDefault("'EMPTY'")
    @Column(name = "business_place_surroundings_name", nullable = false)
    private String businessPlaceSurroundingsName;

    @ColumnDefault("'EMPTY'")
    @Column(name = "sanitary_business_details_name", nullable = false)
    private String sanitaryBusinessDetailsName;

    @ColumnDefault("'EMPTY'")
    @Column(name = "sanitary_business_name", nullable = false)
    private String sanitaryBusinessName;

    @ColumnDefault("-1")
    @Column(name = "total_worker_number", nullable = false)
    private Integer totalWorkerNum;

    @ColumnDefault("'EMPTY'")
    @Column(name = "road_name_address", nullable = false)
    private String roadNameAddress;

    @ColumnDefault("-1")
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @ColumnDefault("-1")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @ColumnDefault("-1")
    @Column(name = "latitude", nullable = false)
    private Double latitude;
}
