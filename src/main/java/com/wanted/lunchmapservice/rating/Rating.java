package com.wanted.lunchmapservice.rating;

import com.wanted.lunchmapservice.common.BaseTime;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@DynamicInsert
@Entity
public class Rating extends BaseTime {

    @Id
    @Column(name = "rating_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("-1")
    @Column(name = "score", updatable = false)
    private Integer score;

    @ColumnDefault("'EMPTY'")
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public static Rating of(Integer score,String content, User user) {
        return Rating.builder()
            .score(score)
            .content(content)
            .user(user)
            .build();
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
