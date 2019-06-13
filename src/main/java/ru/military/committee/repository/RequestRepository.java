package ru.military.committee.repository;

import ru.military.committee.domain.request.Request;
import ru.military.committee.domain.request.RequestStatus;
import ru.military.committee.domain.request.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllRequestByRecruitRecruitId(Long recruitId);

    List<Request> findRequestsByPriorityAndSpecialty_Faculty_FacultyId(short priority, short facultyId);

    Request findRequestByRecruitRecruitIdAndPriority(Long recruitId, short priority);

    List<Request> findRequestsByPriorityAndRequestDateGreaterThan(short priority, Date date);

    List<Request> findRequestsByRequestStatusStatusIdAndRequestDateGreaterThan(byte statusId, Date date);

    List<Request> findRequestsByRequestDateGreaterThan(Date date);

    List<Request> findRequestsBySpecialtyAndRequestDateGreaterThanAndRequestStatus(Specialty specialty, Date date, RequestStatus requestStatus);
}
