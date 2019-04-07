package com.example.committee.service;

import com.example.committee.domain.personal.Exam;
import com.example.committee.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    @Autowired
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Exam getExamByRecruitId(Long recruitId) {
        return this.examRepository.findExamByRecruitRecruitId(recruitId);
    }

    public void addExam(Exam exam) {
        this.examRepository.save(exam);
    }
}
