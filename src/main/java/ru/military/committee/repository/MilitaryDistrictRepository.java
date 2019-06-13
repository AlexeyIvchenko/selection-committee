package ru.military.committee.repository;

import ru.military.committee.domain.location.MilitaryDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilitaryDistrictRepository extends JpaRepository<MilitaryDistrict, Short> {
}
