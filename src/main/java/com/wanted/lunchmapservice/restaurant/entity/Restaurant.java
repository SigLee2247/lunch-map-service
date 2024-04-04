package com.wanted.lunchmapservice.restaurant.entity;

import com.wanted.lunchmapservice.common.BaseTime;
import com.wanted.lunchmapservice.location.entity.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Entity
public class Restaurant extends BaseTime {
    @Id
    @Column(name = "restaurant_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ColumnDefault("'EMPTY'")
    @Column(name = "name", nullable = false)
    private String name;

    @ColumnDefault("'EMPTY'")
    @Column(name = "lot_number_address", nullable = false)
    private String lotNumberAddress;

    @ColumnDefault("'EMPTY'")
    @Column(name = "road_name_address", nullable = false)
    private String roadNameAddress;

    @ColumnDefault("'EMPTY'")
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @ColumnDefault("-1")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @ColumnDefault("-1")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @ColumnDefault("-1")
    @Column(name = "average_score", nullable = false)
    private Double averageScore;
}
