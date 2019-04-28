package com.example.committee.service;

import com.example.committee.domain.request.Request;
import com.example.committee.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
