package com.example.committee.repository;

import com.example.committee.domain.location.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
