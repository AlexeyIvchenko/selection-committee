package com.example.committee.service;

import com.example.committee.domain.personal.Passport;
import com.example.committee.domain.personal.Platoon;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.repository.PassportRepository;
import com.example.committee.repository.RecruitRepository;
import net.sf.jasperreports.charts.JRDataRange;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecruitService {
    @Autowired
    private final RecruitRepository recruitRepository;

    public RecruitService(RecruitRepository recruitRepository) {
        this.recruitRepository = recruitRepository;
    }

    public List<Recruit> getAllRecruits() {
        return recruitRepository.findAll();
    }

    public Recruit getRecruitById(Long recruitId) {
        return recruitRepository.findByRecruitId(recruitId);
    }

    public void addRecruit(Recruit recruit) {
        this.recruitRepository.save(recruit);
    }

    public void deleteRecruitById(Long recruitId) {
        this.recruitRepository.deleteById(recruitId);
    }

    public List<Recruit> getRecruitsByPlatoonsList(List<Platoon> platoons) {
        return this.recruitRepository.findAllByPlatoonIn(platoons);
    }

    public JRDataSource getDataSource() {
        Collection<Recruit> list = recruitRepository.findAll();
        return new JRBeanCollectionDataSource(list);
    }
}
