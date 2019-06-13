package ru.military.committee.service;

import ru.military.committee.domain.personal.Company;
import ru.military.committee.domain.personal.Platoon;
import ru.military.committee.repository.PlatoonRepository;
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

    public List<Platoon> getPlatoonsByCompany(Company company) {
        return this.platoonRepository.findAllByCompany(company);
    }
}
