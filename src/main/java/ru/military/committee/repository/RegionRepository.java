package ru.military.committee.repository;

import ru.military.committee.domain.location.MilitaryDistrict;
import ru.military.committee.domain.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByMilitaryDistrict(MilitaryDistrict militaryDistrict);
}
