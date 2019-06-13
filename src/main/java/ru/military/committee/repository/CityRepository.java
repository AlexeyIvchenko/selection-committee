package ru.military.committee.repository;

import ru.military.committee.domain.location.City;
import ru.military.committee.domain.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findCitiesByRegion(Region region);
}
