package com.example.committee.service;

import com.example.committee.domain.personal.Company;
import com.example.committee.domain.personal.Platoon;
import com.example.committee.repository.PlatoonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoonService {
    private final PlatoonRepository platoonRepository;

    public PlatoonService(PlatoonRepository platoonRepository) {
        this.platoonRepository = platoonRepository;
    }

    public void addPlatoon(Platoon platoon) {
        this.platoonRepository.save(platoon);
    }

    public List<Platoon> getPlatoonsByCompaniesList(List<Company> companies) {
        return this.platoonRepository.findAllByCompanyIn(companies);
    }
}
