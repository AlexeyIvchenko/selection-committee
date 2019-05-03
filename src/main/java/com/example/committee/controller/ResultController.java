package com.example.committee.controller;

import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Request;
import com.example.committee.domain.request.RequestStatus;
import com.example.committee.domain.request.Specialty;
import com.example.committee.service.*;
import com.example.committee.utils.DateWorker;
import com.example.committee.utils.RequestAndScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ResultController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private RequestStatusService requestStatusService;


    @GetMapping(value = "/user/enrollmentPage")
    public String getEnrollmentPage(Model model) {
        List<Request> waitingRequsts = requestService.getRequestsByStatusIdAndRequestYear((byte) 0, DateWorker.getFirstDateInCurrentYear());
        model.addAttribute("waitingRequsts", waitingRequsts);

        List<Request> requests = requestService.getRequestsByStatusIdAndRequestYear((byte) 1, DateWorker.getFirstDateInCurrentYear());
        List<RequestAndScore> requestAndScoreList = transformRequestsListToRequestAndScoreList(requests);
        model.addAttribute("preliminaryList", requestAndScoreList);
        return "enrollmentPage";
    }


    @GetMapping(value = "/user/clearPreliminaryLists")
    public String clearPreliminaryLists() {
        List<Request> requests = requestService.getRequestsByRequestYear(DateWorker.getFirstDateInCurrentYear());
        for (int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            markRequestWithStatus(request, (byte) 0);
        }

        return "redirect:/user/enrollmentPage";
    }

    @GetMapping(value = "/user/getPreliminaryLists")
    public String getPreliminaryLists() {
        List<Specialty> specialtiesList = specialtyService.getAllSpecialties();
        List<ArrayList<RequestAndScore>> preliminaryLists = getEmptyPreliminaryLists(specialtiesList);

        List<Request> requests = requestService.getRequestsByPriorityAndRequestYear((short) 1, DateWorker.getFirstDateInCurrentYear());
        List<RequestAndScore> requestAndScoreList = transformRequestsListToRequestAndScoreList(requests);
        sortRequestAndScoreListByPriorityAndScore(requestAndScoreList);

        for (int i = 0; i < requestAndScoreList.size(); i++) {
            int specialtyId = requestAndScoreList.get(i).getRequest().getSpecialty().getSpecialtyId();

            List<RequestAndScore> preliminarySpecialtyList = preliminaryLists.get(specialtyId - 1);//TODO: 5 заменить на значение из базы по количеству мест в специальности
            RequestAndScore currentReqAndScore = requestAndScoreList.get(i);

            if (preliminarySpecialtyList.size() < 5) {
                preliminarySpecialtyList.add(currentReqAndScore);
                sortRequestAndScoreListByScore(preliminarySpecialtyList);
            } else {
                //Если места на этой специальности заняты, то проверяем вытеснится ли нижний реквест новым (по количеству баллов)
                RequestAndScore lowestReqAndScore = preliminarySpecialtyList.get(preliminarySpecialtyList.size() - 1);
                if (lowestReqAndScore.getScore() <= currentReqAndScore.getScore()) {
                    //Баллы без учета ФИЗО (Требуются, если общие баллы у двух абитуриентов равны)
                    int lowestExamScore = lowestReqAndScore.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(lowestReqAndScore.getRequest().getSpecialty().getFaculty());
                    int currentExamScore = currentReqAndScore.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty((currentReqAndScore.getRequest().getSpecialty().getFaculty()));

                    if ((lowestReqAndScore.getScore() < currentReqAndScore.getScore()) || (lowestExamScore < currentExamScore)) {
                        //Ставим вытесненному реквесту в базе статус "Не рекомендован к зачислению" (по конкретно этому приоритету!!!)
                        Request rejectRequest = lowestReqAndScore.getRequest();
                        markRequestWithStatus(rejectRequest, (byte) 3);

                        //Заменяем реквест с меньшим количеством баллов на реквест с большим количеством баллов
                        preliminarySpecialtyList.remove(preliminarySpecialtyList.size() - 1);
                        preliminarySpecialtyList.add(currentReqAndScore);
                        sortRequestAndScoreListByScore(preliminarySpecialtyList);

                        //Добавляем в конец списка нераспределенных реквестов реквест абитуриента, но с приоритетом ниже на один (но не ниже 3)
                        addLowerPriorityRequestToList(rejectRequest, requestAndScoreList);
                    }
                } else {
                    //Если места на этой специальности заняты, но новый реквест не вытесняет нижний, то ставим новому реквесту статус "Не рекомендован к зачислению" (по конкретно этому приоритету!!!)
                    Request currentRequest = currentReqAndScore.getRequest();
                    markRequestWithStatus(currentRequest, (byte) 3);

                    //Добавляем в конец списка нераспределенных реквестов реквест абитуриента, но с приоритетом ниже на один (но не ниже 3)
                    addLowerPriorityRequestToList(currentRequest, requestAndScoreList);
                }
            }
        }

        //"Победившие" реквесты отмечаем как рекомендованные
        for (int i = 0; i < preliminaryLists.size(); i++) {
            for (int j = 0; j < preliminaryLists.get(i).size(); j++) {
                Request request = preliminaryLists.get(i).get(j).getRequest();
                if (request.getPriority() == 1) {
                    Request secondPriorityRequest = requestService.getRequestByRecruitIdAndPriority(request.getRecruit().getRecruitId(), (short) 2);
                    Request thirdPriorityRequest = requestService.getRequestByRecruitIdAndPriority(request.getRecruit().getRecruitId(), (short) 3);
                    markRequestWithStatus(secondPriorityRequest, (byte) 2);
                    markRequestWithStatus(thirdPriorityRequest, (byte) 2);
                } else {
                    if (request.getPriority() == 2) {
                        Request thirdPriorityRequest = requestService.getRequestByRecruitIdAndPriority(request.getRecruit().getRecruitId(), (short) 3);
                        markRequestWithStatus(thirdPriorityRequest, (byte) 2);
                    }
                }
                markRequestWithStatus(request, (byte) 1);
            }
        }
        return "redirect:/user/enrollmentPage";
    }

    private void addLowerPriorityRequestToList(Request higherPriorityrequest, List<RequestAndScore> requestAndScoreList) {
        if (higherPriorityrequest.getPriority() < 3) {
            Request lowerPriorityRequest = requestService.getRequestByRecruitIdAndPriority(higherPriorityrequest.getRecruit().getRecruitId(), (short) (higherPriorityrequest.getPriority() + 1));
            if (lowerPriorityRequest != null) {
                requestAndScoreList.add(transformRequestToRequestAndScore(lowerPriorityRequest));
            }
        }
    }

    private void markRequestWithStatus(Request request, byte statusId) {
        RequestStatus requestStatus = requestStatusService.getRequestStatusById(statusId);
        request.setRequestStatus(requestStatus);
        requestService.addRequest(request);
    }

    private List<ArrayList<RequestAndScore>> getEmptyPreliminaryLists(List<Specialty> specialtiesList) {
        List<ArrayList<RequestAndScore>> preliminaryLists = new ArrayList<>();

        for (int i = 0; i < specialtiesList.size(); i++) {
            preliminaryLists.add(new ArrayList<>());
        }
        return preliminaryLists;
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

    private List<RequestAndScore> sortRequestAndScoreListByScore(List<RequestAndScore> requestAndScoreList) {
        Collections.sort(requestAndScoreList, (ras1, ras2) -> {
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


        });
        return requestAndScoreList;
    }

    private List<RequestAndScore> sortRequestAndScoreListByPriorityAndScore(List<RequestAndScore> requestAndScoreList) {
        Collections.sort(requestAndScoreList, (ras1, ras2) -> {
            Integer priority1 = Integer.valueOf(ras1.getRequest().getPriority());
            Integer priority2 = Integer.valueOf(ras2.getRequest().getPriority());
            int priorityComp = priority1.compareTo(priority2);
            if (priorityComp != 0) {
                return priorityComp;
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
