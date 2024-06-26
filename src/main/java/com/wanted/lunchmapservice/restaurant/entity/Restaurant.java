package com.wanted.lunchmapservice.restaurant.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

import com.wanted.lunchmapservice.common.BaseTime;
import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.rating.Rating;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
    @GeneratedValue(strategy = SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_seq", allocationSize = 100)
    private Long id;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Default
    @OneToMany(fetch = LAZY, cascade = CascadeType.PERSIST, mappedBy = "restaurant")
    private List<Rating> ratingList = new ArrayList<>();


    public static Restaurant of(Location location, RawRestaurant rawData) {
        return Restaurant.builder()
            .location(location)
            .name(rawData.getName())
            .lotNumberAddress(rawData.getLotNumberAddress())
            .roadNameAddress(rawData.getRoadNameAddress())
            .zipCode(rawData.getZipCode())
            .longitude(rawData.getLongitude())
            .latitude(rawData.getLatitude())
            .averageScore(0.).build();
    }

    public static Restaurant of(Long id) {
        return Restaurant.builder()
            .id(id)
            .build();
    }

    public boolean isSame(RawRestaurant rawRestaurant) {
        return name.equals(rawRestaurant.getName())
            && lotNumberAddress.equals(rawRestaurant.getLotNumberAddress());
    }

    public void update(Location location, RawRestaurant rawData) {
        this.location = location;
        this.name = rawData.getName();
        this.lotNumberAddress = rawData.getLotNumberAddress();
        this.roadNameAddress = rawData.getRoadNameAddress();
        this.zipCode = rawData.getZipCode();
        this.longitude = rawData.getLongitude();
        this.latitude = rawData.getLatitude();
    }

    public void addRating(Rating rating) {
        double savedScore = this.ratingList.stream().mapToInt(d->d.getScore()).sum();
        this.ratingList.add(rating);
        rating.addRestaurant(this);
        calculateAverageScore(savedScore,rating.getScore(),ratingList.size());

    }

    private void calculateAverageScore(double savedScore, int newScore, int size) {
        double avg = (savedScore + newScore) / size;
        this.averageScore =  Math.round(avg * 10) /10.0;
    }
}
