package ru.military.committee.repository;

import ru.military.committee.domain.location.Office;
import ru.military.committee.domain.personal.Platoon;
import ru.military.committee.domain.personal.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    List<Recruit> findAllByPlatoonIn(List<Platoon> platoons);

    Recruit findByRecruitId(Long recruitId);

    List<Recruit> findByRegistrationYearAndOfficeIn(short registrationYear, List<Office> officesList);
}
