package ru.military.committee.service;

import ru.military.committee.domain.standarts.Run3Standart;
import ru.military.committee.repository.Run3StandartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Run3StandartService {
    @Autowired
    private final Run3StandartRepository run3StandartRepository;

    public Run3StandartService(Run3StandartRepository run3StandartRepository) {
        this.run3StandartRepository = run3StandartRepository;
    }

    public byte getScoreByResult(double result) {
        List<Run3Standart> standarts = this.run3StandartRepository.findAll();
        if (result > standarts.get(0).getResult()) {
            return 0;
        }
        if (result <= standarts.get(standarts.size() - 1).getResult()) {
            return 100;
        }
        for (int i = 0; i < standarts.size(); i++) {
            Run3Standart standart = standarts.get(i);
            Run3Standart nextStandart = standarts.get(i + 1);
            if (result <= standart.getResult() && result > nextStandart.getResult()) {
                return standart.getScore();
            }
        }
        return 0;
    }

    public double getResultByScore(byte score) {
        List<Run3Standart> standarts = this.run3StandartRepository.findAll();
        if (score < standarts.get(0).getScore()) {
            return 0;
        }
        if (score >= standarts.get(standarts.size() - 1).getScore()) {
            return 10.30;
        }
        for (int i = 0; i < standarts.size(); i++) {
            Run3Standart standart = standarts.get(i);
            Run3Standart nextStandart = standarts.get(i + 1);
            if (score >= standart.getScore() && score < nextStandart.getScore()) {
                return standart.getResult();
            }
        }
        return 0.0;
    }
}
