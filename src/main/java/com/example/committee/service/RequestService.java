package com.example.committee.service;

import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Request;
import com.example.committee.domain.request.RequestStatus;
import com.example.committee.domain.request.Specialty;
import com.example.committee.repository.RequestRepository;
import com.example.committee.repository.RequestStatusRepository;
import com.example.committee.utils.DateWorker;
import com.example.committee.utils.ReportHelper;
import com.example.committee.utils.RequestAndScore;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RequestService {

    @Autowired
    private final RequestRepository requestRepository;
    @Autowired
    private final RequestStatusRepository requestStatusRepository;

    public RequestService(RequestRepository requestRepository, RequestStatusRepository requestStatusRepository) {
        this.requestRepository = requestRepository;
        this.requestStatusRepository = requestStatusRepository;
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
        RequestStatus requestStatus1 = requestStatusRepository.findByStatusId((byte) 1);
        RequestStatus requestStatus3 = requestStatusRepository.findByStatusId((byte) 3);

        List<Request> enteredRequests = requestRepository.findRequestsBySpecialtyAndRequestDateGreaterThanAndRequestStatus(specialty, DateWorker.getFirstDateInCurrentYear(), requestStatus1);
        List<Request> rejectedRequests = requestRepository.findRequestsBySpecialtyAndRequestDateGreaterThanAndRequestStatus(specialty, DateWorker.getFirstDateInCurrentYear(), requestStatus3);
        enteredRequests.addAll(rejectedRequests);

        List<RequestAndScore> requestAndScoreList = transformRequestsListToRequestAndScoreList(enteredRequests);
        sortRequestAndScoreListByRequestStatusAndScore(requestAndScoreList);

        List<ReportHelper> reportHelpers = new ArrayList<>();
        for (int i = 0; i < requestAndScoreList.size(); i++) {
            reportHelpers.add(new ReportHelper(requestAndScoreList.get(i).getRequest(), i + 1));
        }
        Collection<ReportHelper> list = reportHelpers;
        return new JRBeanCollectionDataSource(list);
    }

    private List<RequestAndScore> transformRequestsListToRequestAndScoreList(List<Request> requestsList) {
        List<RequestAndScore> requestAndScoreList = new ArrayList<>();
        for (int i = 0; i < requestsList.size(); i++) {
            requestAndScoreList.add(transformRequestToRequestAndScore(requestsList.get(i)));
        }
        return requestAndScoreList;
    }

    private RequestAndScore transformRequestToRequestAndScore(Request request) {
        Recruit recruit = request.getRecruit();
        int recruitScore = recruit.sumTotalRecruitScore(request.getSpecialty().getFaculty());
        return new RequestAndScore(request, recruitScore);
    }

    private List<RequestAndScore> sortRequestAndScoreListByRequestStatusAndScore(List<RequestAndScore> requestAndScoreList) {
        Collections.sort(requestAndScoreList, (ras1, ras2) -> {
            Integer status1 = Integer.valueOf(ras1.getRequest().getRequestStatus().getStatusId());
            Integer status2 = Integer.valueOf(ras2.getRequest().getRequestStatus().getStatusId());
            int statusComp = status1.compareTo(status2);
            if (statusComp != 0) {
                return statusComp;
            } else {
                Integer score1 = Integer.valueOf(ras1.getScore());
                Integer score2 = Integer.valueOf(ras2.getScore());
                int scoreComp = score2.compareTo(score1);
                if (scoreComp != 0) {
                    return scoreComp;
                } else {
                    Integer examScore1 = Integer.valueOf(ras1.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(ras1.getRequest().getSpecialty().getFaculty()));
                    Integer examScore2 = Integer.valueOf(ras2.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(ras2.getRequest().getSpecialty().getFaculty()));
                    return examScore2.compareTo(examScore1);
                }

            }
        });
        return requestAndScoreList;
    }
}
