package com.example.committee.service;

import com.example.committee.domain.personal.Nationality;
import com.example.committee.repository.NationalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationalityService {
    @Autowired
    private final NationalityRepository nationalityRepository;

    public NationalityService(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    public List<Nationality> getAllNationalities() {
        return this.nationalityRepository.findAll();
    }
}
