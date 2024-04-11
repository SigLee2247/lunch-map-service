package com.wanted.lunchmapservice.location.entity;

import static jakarta.persistence.GenerationType.SEQUENCE;

import com.wanted.lunchmapservice.common.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Location extends BaseTime {
    @Id
    @Column(name = "location_id", updatable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "location_seq", allocationSize = 100)
    private Long id;

    @ColumnDefault("'EMPTY'")
    @Column(name = "city_name", nullable = false)
    private String cityName;

    @ColumnDefault("'EMPTY'")
    @Column(name = "country_name", nullable = false)
    private String countryName;

    @ColumnDefault("-1")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @ColumnDefault("-1")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    public String getCode() {
        return cityName.substring(0, 2) + " " + countryName;
    }

    public void update(Location newLocation) {
        this.cityName = newLocation.getCityName();
        this.countryName = newLocation.getCountryName();
        this.longitude = newLocation.getLongitude();
        this.latitude = newLocation.getLatitude();
    }
}
