package ru.military.committee.service;

import ru.military.committee.domain.personal.ExtranceTest;
import ru.military.committee.repository.ExtranceTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtranceTestService {
    @Autowired
    private final ExtranceTestRepository extranceTestRepository;

    public ExtranceTestService(ExtranceTestRepository extranceTestRepository) {
        this.extranceTestRepository = extranceTestRepository;
    }

    public void addExtranceTest(ExtranceTest extranceTest) {
        this.extranceTestRepository.save(extranceTest);
    }
}
