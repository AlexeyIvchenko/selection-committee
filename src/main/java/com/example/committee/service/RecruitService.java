package com.example.committee.service;

import com.example.committee.domain.personal.Recruit;
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

    public List<Recruit> findAll() {
        return recruitRepository.findAll();
    }

    public Recruit findById(Long recruitId) {
        return recruitRepository.findById(recruitId).orElse(null);
    }

    public void addRecruit(Recruit recruit) {
        this.recruitRepository.save(recruit);
    }

    public JRDataSource getDataSource() {
        Collection<Recruit> list = recruitRepository.findAll();
        return new JRBeanCollectionDataSource(list);
    }
}
