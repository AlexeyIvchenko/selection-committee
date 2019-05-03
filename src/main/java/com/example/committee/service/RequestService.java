package com.example.committee.service;

import com.example.committee.domain.request.Request;
import com.example.committee.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class RequestService {

    @Autowired
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequestsByRecruitId(Long recruitId) {
        return this.requestRepository.findAllRequestByRecruitRecruitId(recruitId);
    }

    public void addRequest(Request request) {
        this.requestRepository.save(request);
    }

    public List<Request> getRequestsByPriorityAndSpecialty_Faculty_FacultyId(short priority, short facultyId) {
        return this.requestRepository.findRequestsByPriorityAndSpecialty_Faculty_FacultyId(priority, facultyId);
    }

    public Request getRequestByRecruitIdAndPriority(Long recruitId, short priority) {
        return this.requestRepository.findRequestByRecruitRecruitIdAndPriority(recruitId, priority);
    }

    public List<Request> getRequestsByPriorityAndRequestYear(short priority, Date date) {
        return this.requestRepository.findRequestsByPriorityAndRequestDateGreaterThan(priority, date);
    }

    public List<Request> getRequestsByRequestYear(Date date) {
        return this.requestRepository.findRequestsByRequestDateGreaterThan(date);
    }

    public Request getRequestById(Long requestId) {
        return this.requestRepository.findByRequestId(requestId);
    }

    public List<Request> getRequestsByStatusIdAndRequestYear(byte statusId, Date date) {
        return this.requestRepository.findRequestsByRequestStatusStatusIdAndRequestDateGreaterThan(statusId, date);
    }

    public void deleteRequestsByRecruitId(Long recruitId) {
        this.requestRepository.deleteRequestsByRecruitRecruitId(recruitId);
    }
}
