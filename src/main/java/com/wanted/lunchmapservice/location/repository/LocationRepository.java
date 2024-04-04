package com.wanted.lunchmapservice.location.repository;


import com.wanted.lunchmapservice.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
