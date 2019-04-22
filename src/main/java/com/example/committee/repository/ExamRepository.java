package com.example.committee.repository;

import com.example.committee.domain.personal.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Exam findExamByRecruitRecruitId(Long recruitId);
}
