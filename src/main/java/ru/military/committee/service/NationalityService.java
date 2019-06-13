package ru.military.committee.service;

import ru.military.committee.domain.personal.Nationality;
import ru.military.committee.repository.NationalityRepository;
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
