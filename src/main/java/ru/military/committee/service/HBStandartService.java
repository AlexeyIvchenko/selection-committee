package ru.military.committee.service;

import ru.military.committee.domain.standarts.HBStandart;
import ru.military.committee.repository.HBStandartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HBStandartService {
    @Autowired
    private final HBStandartRepository hbStandartRepository;

    public HBStandartService(HBStandartRepository hbStandartRepository) {
        this.hbStandartRepository = hbStandartRepository;
    }

    public byte getScoreByResult(byte result) {
        HBStandart standart = this.hbStandartRepository.findByResult(result);
        return standart.getScore();
    }

    public byte getResultByScore(byte score) {
        HBStandart standart = this.hbStandartRepository.findByScore(score);
        return standart.getResult();
    }
}
