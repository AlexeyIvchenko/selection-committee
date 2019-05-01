package com.example.committee.repository;

import com.example.committee.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findRequestByRecruitRecruitId(Long recruitId);

    List<Request> findAllRequestByRecruitRecruitId(Long recruitId);

    List<Request> findRequestsByPriorityAndSpecialty_Faculty_FacultyId(short priority, short facultyId);

    Request findRequestByRecruitRecruitIdAndPriority(Long recruitId, short priority);

    List<Request> findRequestsByPriorityAndRequestDateGreaterThan(short priority, Date date);

    Request findByRequestId(Long requestId);
}
