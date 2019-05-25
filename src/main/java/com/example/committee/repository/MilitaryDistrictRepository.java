package com.example.committee.repository;

import com.example.committee.domain.location.MilitaryDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilitaryDistrictRepository extends JpaRepository<MilitaryDistrict, Short> {
}
