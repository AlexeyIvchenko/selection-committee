package com.example.committee.repository;

import com.example.committee.domain.personal.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

}
