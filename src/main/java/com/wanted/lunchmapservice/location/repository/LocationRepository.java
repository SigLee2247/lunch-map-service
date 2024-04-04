package com.wanted.lunchmapservice.location.repository;


import com.wanted.lunchmapservice.location.entity.Location;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {

  @Query("select l from Location l where l.cityName LIKE :citryName% and l.countryName = :countryName")
  Optional<Location> findByLocationCode(@Param("cityName") String cityName,@Param("countryName") String countryName);
}
