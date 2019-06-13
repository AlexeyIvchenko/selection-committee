package ru.military.committee.service;

import ru.military.committee.domain.location.Region;
import ru.military.committee.repository.RegionRepository;
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
