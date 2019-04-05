package com.example.committee.repository;

import com.example.committee.domain.location.City;
import com.example.committee.domain.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findCitiesByRegion(Region region);
}
