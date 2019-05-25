package com.example.committee.service;

import com.example.committee.domain.location.MilitaryDistrict;
import com.example.committee.domain.location.Office;
import com.example.committee.domain.location.Region;
import com.example.committee.domain.personal.Platoon;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.repository.MilitaryDistrictRepository;
import com.example.committee.repository.OfficeRepository;
import com.example.committee.repository.RecruitRepository;
import com.example.committee.repository.RegionRepository;
import com.example.committee.utils.DateWorker;
import com.example.committee.utils.StatisticHelper;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecruitService {
    @Autowired
    private final RecruitRepository recruitRepository;
    @Autowired
    private final MilitaryDistrictRepository districtRepository;
    @Autowired
    private final RegionRepository regionRepository;
    @Autowired
    private final OfficeRepository officeRepository;

    public RecruitService(RecruitRepository recruitRepository, MilitaryDistrictRepository districtRepository, RegionRepository regionRepository, OfficeRepository officeRepository) {
        this.recruitRepository = recruitRepository;
        this.districtRepository = districtRepository;
        this.regionRepository = regionRepository;
        this.officeRepository = officeRepository;
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

    public JRDataSource getDataSource(short registrationYear) {
        List<MilitaryDistrict> militaryDistricts = districtRepository.findAll();
        List<StatisticHelper> statisticHelpers = new ArrayList<>();

        for (int i = 0; i < militaryDistricts.size(); i++) {
            List<Region> regionsList = regionRepository.findByMilitaryDistrict(militaryDistricts.get(i));
            List<Office> officesList = officeRepository.findByRegionIn(regionsList);
            List<Recruit> recruitsList = recruitRepository.findByRegistrationYearAndOfficeIn(registrationYear, officesList);


            if (recruitsList.size() != 0) {
                long civilMaleCount = recruitsList.stream().filter(rec -> Arrays.asList(new Byte[]{1, 2, 3, 4}).contains(rec.getCategory().getCategoryId()) && (rec.isSex())).collect(Collectors.toList()).size();
                long contractMaleCount = recruitsList.stream().filter(rec -> Arrays.asList(new Byte[]{5}).contains(rec.getCategory().getCategoryId()) && (rec.isSex())).collect(Collectors.toList()).size();
                long conscriptMaleCount = recruitsList.stream().filter(rec -> Arrays.asList(new Byte[]{6}).contains(rec.getCategory().getCategoryId()) && (rec.isSex())).collect(Collectors.toList()).size();

                long civilFemaleCount = recruitsList.stream().filter(rec -> Arrays.asList(new Byte[]{1, 2, 3, 4}).contains(rec.getCategory().getCategoryId()) && (!rec.isSex())).collect(Collectors.toList()).size();
                long contractFemaleCount = recruitsList.stream().filter(rec -> Arrays.asList(new Byte[]{5}).contains(rec.getCategory().getCategoryId()) && (!rec.isSex())).collect(Collectors.toList()).size();
                long conscriptFemaleCount = recruitsList.stream().filter(rec -> Arrays.asList(new Byte[]{6}).contains(rec.getCategory().getCategoryId()) && (!rec.isSex())).collect(Collectors.toList()).size();

                StatisticHelper statisticHelper = new StatisticHelper();
                statisticHelper.setMilitaryDistrictName(militaryDistricts.get(i).getDistrictName());
                statisticHelper.setCivilMaleCount(String.valueOf(civilMaleCount));
                statisticHelper.setContractMaleCount(String.valueOf(contractMaleCount));
                statisticHelper.setConscriptMaleCount(String.valueOf(conscriptMaleCount));
                statisticHelper.setCivilFemaleCount(String.valueOf(civilFemaleCount));
                statisticHelper.setContractFemaleCount(String.valueOf(contractFemaleCount));
                statisticHelper.setConscriptFemaleCount(String.valueOf(conscriptFemaleCount));
                statisticHelper.setCurrentDate(DateWorker.getCurrentDate());

                statisticHelpers.add(statisticHelper);
            }
        }
        Collection<StatisticHelper> list = statisticHelpers;
        return new JRBeanCollectionDataSource(list);
    }
}
