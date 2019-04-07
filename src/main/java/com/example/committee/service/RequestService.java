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

    public Request getRequestByRecruitId(Long recruitId) {
        return this.requestRepository.findRequestByRecruitRecruitId(recruitId);
    }

    public List<Request> getAllRequestsByRecruitId(Long recruitId) {
        return this.requestRepository.findAllRequestByRecruitRecruitId(recruitId);
    }
}
