package com.example.committee.repository;

import com.example.committee.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, String> {
    Request findRequestByRecruitRecruitId(Long recruitId);

    List<Request> findAllRequestByRecruitRecruitId(Long recruitId);

    List<Request> findRequestsByPriorityAndSpecialty_Faculty_FacultyId(short priority, short facultyId);
}
