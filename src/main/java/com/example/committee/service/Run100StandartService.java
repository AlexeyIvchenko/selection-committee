package com.example.committee.service;

import com.example.committee.domain.standarts.HBStandart;
import com.example.committee.domain.standarts.Run100Standart;
import com.example.committee.repository.Run100StandartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Run100StandartService {
    @Autowired
    private final Run100StandartRepository run100StandartRepository;

    public Run100StandartService(Run100StandartRepository run100StandartRepository) {
        this.run100StandartRepository = run100StandartRepository;
    }

    public byte getScoreByResult(double result) {
        List<Run100Standart> standarts = this.run100StandartRepository.findAll();
        if (result > standarts.get(0).getResult()) {
            return 0;
        }
        if (result <= standarts.get(standarts.size() - 1).getResult()) {
            return 100;
        }
        for (int i = 0; i < standarts.size(); i++) {
            Run100Standart standart = standarts.get(i);
            Run100Standart nextStandart = standarts.get(i + 1);
            if (result <= standart.getResult() && result > nextStandart.getResult()) {
                return standart.getScore();
            }
        }
        return 0;
    }

    public double getResultByScore(byte score) {
        List<Run100Standart> standarts = this.run100StandartRepository.findAll();
        if (score < standarts.get(0).getScore()) {
            return 0;
        }
        if (score >= standarts.get(standarts.size() - 1).getScore()) {
            return 11.8;
        }
        for (int i = 0; i < standarts.size(); i++) {
            Run100Standart standart = standarts.get(i);
            Run100Standart nextStandart = standarts.get(i + 1);
            if (score >= standart.getScore() && score < nextStandart.getScore()) {
                return standart.getResult();
            }
        }
        return 0.0;
    }
}
