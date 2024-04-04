package com.wanted.lunchmapservice.location.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.wanted.lunchmapservice.location.entity.Location;
import com.wanted.lunchmapservice.location.repository.LocationRepository;
import com.wanted.lunchmapservice.location.utils.dto.LocationCsvDto;
import jakarta.annotation.PostConstruct;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LocationCsvInit {

  private final LocationRepository locationRepository;
  private final static String FILE_NAME = "static/sgg_lat_lon.csv";

  @PostConstruct
  @Transactional
  public void storeLocationCsvData() {
    List<Location> locationDataList = readLocationCsvData().stream()
        .map(LocationCsvDto::toEntity).toList();
    locationRepository.saveAll(locationDataList);
  }

  private List<LocationCsvDto> readLocationCsvData() {
    ClassPathResource resource = new ClassPathResource(FILE_NAME);
    try (Reader reader = new FileReader(resource.getFile())) {
      return new CsvToBeanBuilder<LocationCsvDto>(reader)
          .withType(LocationCsvDto.class)
          .withSkipLines(1) //헤더 스킵
          .build().parse();
    } catch (Exception e) {
      return List.of();
    }
  }
}
