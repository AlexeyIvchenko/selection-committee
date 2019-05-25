package com.example.committee.repository;

import com.example.committee.domain.location.Office;
import com.example.committee.domain.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    List<Office> findByRegionIn(List<Region> regionsList);
}
