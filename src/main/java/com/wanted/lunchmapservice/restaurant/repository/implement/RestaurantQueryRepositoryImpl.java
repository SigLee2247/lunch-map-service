package com.wanted.lunchmapservice.restaurant.repository.implement;

import static com.wanted.lunchmapservice.location.entity.QLocation.location;
import static com.wanted.lunchmapservice.restaurant.entity.QRestaurant.restaurant;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.dto.RestaurantGetFilterDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantQueryRepositoryImpl implements  RestaurantQueryRepository{
  private final JPAQueryFactory query;

  public Page<Restaurant> findPageByFilter(Pageable pageable, RestaurantGetFilterDto condition){
    List<Restaurant> content = query
        .selectFrom(restaurant)
        .join(restaurant.location,location).fetchJoin()
        .where(
            cityNameEq(condition.getCityName()),
            countryNameEq(condition.getCountryName())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> count = query
        .select(restaurant.count())
        .from(restaurant)
        .where(
            cityNameEq(condition.getCityName()),
            countryNameEq(condition.getCountryName())
        );

    return PageableExecutionUtils.getPage(content,pageable, count::fetchOne);
  }

  private BooleanExpression cityNameEq(String cityName) {
    return isEmpty(cityName) ? null : restaurant.location.cityName.eq(cityName);
  }
  private BooleanExpression countryNameEq(String countryName) {
    return isEmpty(countryName) ? null : restaurant.location.countryName.eq(countryName);
  }
}

