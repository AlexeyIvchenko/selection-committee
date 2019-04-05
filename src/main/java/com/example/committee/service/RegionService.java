package com.example.committee.service;

import com.example.committee.domain.location.Region;
import com.example.committee.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getAllRegions() {
        return this.regionRepository.findAll();
    }

    public Region getRegionById(Long regionId) {
        return this.regionRepository.findById(regionId).orElse(null);
    }
}
