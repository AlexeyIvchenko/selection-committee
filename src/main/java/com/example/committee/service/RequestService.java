package com.example.committee.service;

import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Request;
import com.example.committee.domain.request.Specialty;
import com.example.committee.repository.RequestRepository;
import com.example.committee.utils.DateWorker;
import com.example.committee.utils.ReportHelper;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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

    public List<Request> getRequestsByStatusIdAndRequestYear(byte statusId, Date date) {
        return this.requestRepository.findRequestsByRequestStatusStatusIdAndRequestDateGreaterThan(statusId, date);
    }

    public JRDataSource getDataSource(Specialty specialty) {
        List<Request> requests = requestRepository.findRequestsBySpecialtyAndRequestDateGreaterThan(specialty, DateWorker.getFirstDateInCurrentYear());
        List<ReportHelper> reportHelpers = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            reportHelpers.add(new ReportHelper(requests.get(i), i + 1));
        }
        Collection<ReportHelper> list = reportHelpers;
        return new JRBeanCollectionDataSource(list);
    }
}
