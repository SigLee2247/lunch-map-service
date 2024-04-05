package com.wanted.lunchmapservice.restaurant.repository.implement;

import static com.wanted.lunchmapservice.location.entity.QLocation.location;
import static com.wanted.lunchmapservice.restaurant.entity.QRestaurant.restaurant;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.lunchmapservice.restaurant.entity.Restaurant;
import com.wanted.lunchmapservice.restaurant.repository.RestaurantQueryRepository;
import com.wanted.lunchmapservice.restaurant.repository.dto.RequestRestaurantGetFilterDto;
import com.wanted.lunchmapservice.restaurant.controller.dto.NearRestaurantRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantQueryRepositoryImpl implements RestaurantQueryRepository {
  private final JPAQueryFactory query;
  private static final Integer EARTH_RADIUS = 6371;

  @Override
  public Page<Restaurant> findPageByFilter(Pageable pageable, RequestRestaurantGetFilterDto condition){
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

  @Override
  public Page<Restaurant> findNearByRestaurant(NearRestaurantRequestDto dto, Pageable pageable) {
    Double userLat = Double.parseDouble(dto.getCurrentLatitude());
    Double userLng = Double.parseDouble(dto.getCurrentLongitude());

    List<Restaurant> nearByRestaurants = query
        .select(restaurant)
        .from(restaurant)
        .where(acosExpression(userLat, userLng).loe(dto.getRange()))
        .orderBy(getOrderByExpression(userLat, userLng, pageable.getSort()))
        .fetch();

    JPAQuery<Long> count = query.select(restaurant.count())
        .from(restaurant)
        .where(acosExpression(userLat, userLng).loe(dto.getRange()));

    return PageableExecutionUtils.getPage(nearByRestaurants, pageable, count::fetchOne);
  }


  //추 후 정렬 조건이 늘어나면 추가 예정
  private OrderSpecifier<?> getOrderByExpression(Double userLat, Double userLng, Sort sort) {
    return sort.equals(Sort.unsorted()) ? acosExpression(userLat, userLng).asc() : restaurant.averageScore.desc();
  }

  private NumberTemplate<Double> acosExpression(Double userLat, Double userLng) {
    return Expressions.numberTemplate(Double.class,
        "{0} * acos(cos(radians({1})) * cos(radians({2})) * cos(radians({3}) - radians({4})) + "
            + "sin(radians({1})) * sin(radians({2})))",
        EARTH_RADIUS, userLat, restaurant.latitude, userLng, restaurant.longitude);
  }

  private BooleanExpression cityNameEq(String cityName) {
    return isEmpty(cityName) ? null : restaurant.location.cityName.eq(cityName);
  }
  private BooleanExpression countryNameEq(String countryName) {
    return isEmpty(countryName) ? null : restaurant.location.countryName.eq(countryName);
  }
}

