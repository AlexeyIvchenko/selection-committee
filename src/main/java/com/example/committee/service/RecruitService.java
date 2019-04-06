package com.example.committee.service;

import com.example.committee.domain.personal.Passport;
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

    @Autowired
    private final PassportRepository passportRepository;

    public RecruitService(RecruitRepository recruitRepository, PassportRepository passportRepository) {
        this.recruitRepository = recruitRepository;
        this.passportRepository = passportRepository;
    }

    public List<Recruit> getAllRecruits() {
        return recruitRepository.findAll();
    }

    public Recruit findById(Long recruitId) {
        return recruitRepository.findById(recruitId).orElse(null);
    }

    public void addRecruit(Recruit recruit) {
        this.recruitRepository.save(recruit);
    }

    public void updateRecruit(Recruit recruit) {
        this.passportRepository.save(recruit.getPassport());

    }

    public void deleteRecruitById(Long recruitId) {
        this.recruitRepository.deleteById(recruitId);
    }

    public JRDataSource getDataSource() {
        Collection<Recruit> list = recruitRepository.findAll();
        return new JRBeanCollectionDataSource(list);
    }
}
