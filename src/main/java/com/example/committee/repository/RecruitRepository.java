package com.example.committee.repository;

import com.example.committee.domain.personal.Platoon;
import com.example.committee.domain.personal.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    List<Recruit> findAllByPlatoonIn(List<Platoon> platoons);

    Recruit findByRecruitId(Long recruitId);
}
