package com.example.committee.service;

import com.example.committee.domain.location.City;
import com.example.committee.domain.location.Region;
import com.example.committee.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return this.cityRepository.findAll();
    }

    public List<City> getAllCitiesInRegion(Region region) {
        return this.cityRepository.findCitiesByRegion(region);
    }
}
