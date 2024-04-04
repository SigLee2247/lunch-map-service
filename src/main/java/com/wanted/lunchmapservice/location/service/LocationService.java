package com.wanted.lunchmapservice.location.service;

import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.location.repository.LocationRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LocationService {

  private final LocationRepository locationRepository;

  @Transactional
  public List<Location> syncLocationData(List<Location> csvLocationList) {
    List<Location> locationList = locationRepository.findAll();

    Map<String, Location> checkerMap = new ConcurrentHashMap<>();
    //DB에서 가져온 데이터 init
    for (Location location : locationList) {
      checkerMap.put(location.getCode(),location);
    }
    //DB에 추가할 데이터 insert -> 기존에 존재하는 데이터는 update, 아닐 경우 수정
    for (Location location : csvLocationList) {
      Location savedLocation = checkerMap.get(location.getCode());
      if(savedLocation != null){
        savedLocation.update(location);
        checkerMap.put(location.getCode(), savedLocation);
      }else {
        checkerMap.put(location.getCode(),location);
      }

    }

    locationRepository.saveAll(checkerMap.values());
    return locationList;
  }

  public Location getLocation(String cityName, String countryName){
    if(locationRepository.findByLocationCode(cityName, countryName).isPresent())
      return locationRepository.findByLocationCode(cityName, countryName).get();
    else
      throw new RuntimeException("조회할 정보가 존재 하지 않습니다.");
  }
}