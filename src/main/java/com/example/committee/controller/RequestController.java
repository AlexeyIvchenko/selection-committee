package com.example.committee.controller;

import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
import com.example.committee.domain.request.RequestStatus;
import com.example.committee.domain.request.Specialty;
import com.example.committee.service.*;
import com.example.committee.utils.DateWorker;
import com.example.committee.utils.ThreeSpecialtiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RequestController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private RequestStatusService requestStatusService;


    @GetMapping(value = "/user/requestsPage/{recruitId}")
    public String getRequestsPage(@ModelAttribute("tsr") ThreeSpecialtiesRequest threeFacultiesRequest, @PathVariable("recruitId") Long recruitId, Model model) {

        List<Request> waitingRequests = requestService.getRequestsByStatusIdAndRequestYear((byte) 0, DateWorker.getFirstDateInCurrentYear());
        model.addAttribute("waitingRequests", waitingRequests);
        if (waitingRequests.size() == 0) {
            model.addAttribute("stopRequestMessage", "Время подачи заявлений завершено. " +
                    "Если Вам крайне необходимо подать заявление, то перейдите в раздел 'Зачисление' " +
                    "и очистите список рекомендованных к зачислению. После этого вновь откроется возможность подавать заявления");
        } else {
            model.addAttribute("stopRequestMessage", "");
        }

        Recruit recruit = recruitService.getRecruitById(recruitId);
        List<Faculty> facultyList = facultyService.getAllFaculties();
        List<Faculty> facultyListByRecruitExam = new ArrayList<>();

        for (int i = 0; i < facultyList.size(); i++) {
            Faculty faculty = facultyList.get(i);
            if ((!faculty.isGirlAccess()) && (!recruit.isSex())) {
                continue;
            }
            if (faculty.getScoreMath() != -1) {
                if (faculty.getScoreMath() > recruit.getExam().getScoreMath()) continue;
            }
            if (faculty.getScoreRusLang() != -1) {
                if (faculty.getScoreRusLang() > recruit.getExam().getScoreRusLang()) continue;
            }
            if (faculty.getScorePhysics() != -1) {
                if (faculty.getScorePhysics() > recruit.getExam().getScorePhysics()) continue;
            }
            if (faculty.getScoreForeignLang() != -1) {
                if (faculty.getScoreForeignLang() > recruit.getExam().getScoreForeignLang()) continue;
            }
            if (faculty.getScoreHistory() != -1) {
                if (faculty.getScoreHistory() > recruit.getExam().getScoreHistory()) continue;
            }
            if (faculty.getScoreSocial() != -1) {
                if (faculty.getScoreSocial() > recruit.getExam().getScoreSocial()) continue;
            }
            if (faculty.getScoreLiterature() != -1) {
                if (faculty.getScoreLiterature() > recruit.getExam().getScoreLiterature()) continue;
            }
            facultyListByRecruitExam.add(faculty);
        }


        model.addAttribute("facultyList", facultyListByRecruitExam);

        List<Specialty> firstFacultySpecialtiesList = new ArrayList<>();
        if (facultyListByRecruitExam.size() != 0) {
            Faculty faculty = facultyListByRecruitExam.get(0);
            firstFacultySpecialtiesList = specialtyService.getAllSpecialtiesInFaculty(faculty);
        }

        model.addAttribute("firstFacultySpecialtiesList", firstFacultySpecialtiesList);

        model.addAttribute("recruitId", recruitId);

        return "requestsPage";
    }

    @PostMapping("/user/addRequests")
    public String addRequests(@ModelAttribute("tfr") ThreeSpecialtiesRequest threeSpecialtiesRequest, @RequestParam(value = "recruitId") Long recruitId) {
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        Specialty firstPrioritySpecialty = threeSpecialtiesRequest.getFirstPriority();
        Specialty secondPrioritySpecialty = threeSpecialtiesRequest.getSecondPriority();
        Specialty thirdPrioritySpecialty = threeSpecialtiesRequest.getThirdPriority();

        Date currentDate = DateWorker.getCurrentDate();
        RequestStatus requestStatus = requestStatusService.getRequestStatusById((byte) 0);
        if (firstPrioritySpecialty != null) {
            Request firstPriorityRequest = new Request(selectedRecruit, firstPrioritySpecialty, (short) 1, currentDate, requestStatus);
            requestService.addRequest(firstPriorityRequest);
        }
        if (secondPrioritySpecialty != null) {
            Request secondPriorityRequest = new Request(selectedRecruit, secondPrioritySpecialty, (short) 2, currentDate, requestStatus);
            requestService.addRequest(secondPriorityRequest);
        }
        if (thirdPrioritySpecialty != null) {
            Request thirdPriorityRequest = new Request(selectedRecruit, thirdPrioritySpecialty, (short) 3, currentDate, requestStatus);
            requestService.addRequest(thirdPriorityRequest);
        }
        return "redirect:/user/recruitsListPage";
    }

    @RequestMapping(value = "/user/specialties", method = RequestMethod.GET)
    public @ResponseBody
    List<Specialty> findSpecialtiesByFaculty(@RequestParam(value = "facultyId", required = true) short facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        List<Specialty> specialtiesList = specialtyService.getAllSpecialtiesInFaculty(faculty);
        return specialtiesList;
    }

    @GetMapping(value = "/user/editRequestsPage/{recruitId}")
    public String getEditRequestsPage(@PathVariable("recruitId") Long recruitId, Model model) {
        List<Request> requestsList = requestService.getAllRequestsByRecruitId(recruitId);
        ThreeSpecialtiesRequest tsr = new ThreeSpecialtiesRequest();
        Optional<Request> firstPriorityRequest = requestsList.stream().filter(req -> req.getPriority() == 1).findFirst();
        Specialty firstPrioritySpecialty = firstPriorityRequest.get().getSpecialty();
        tsr.setFirstPriority(firstPrioritySpecialty);

        Optional<Request> secondPriorityRequest = requestsList.stream().filter(req -> req.getPriority() == 2).findFirst();
        Specialty secondPrioritySpecialty = secondPriorityRequest.get().getSpecialty();
        tsr.setSecondPriority(secondPrioritySpecialty);

        Optional<Request> thirdPriorityRequest = requestsList.stream().filter(req -> req.getPriority() == 3).findFirst();
        Specialty thirdPrioritySpecialty = thirdPriorityRequest.get().getSpecialty();
        tsr.setThirdPriority(thirdPrioritySpecialty);

        tsr.setFirstPriorityFaculty(firstPrioritySpecialty.getFaculty());
        tsr.setSecondPriorityFaculty(secondPrioritySpecialty.getFaculty());
        tsr.setThirdPriorityFaculty(thirdPrioritySpecialty.getFaculty());

        model.addAttribute("tsr", tsr);
        model.addAttribute("recruitId", recruitId);
        List<Specialty> specialtiesList = specialtyService.getAllSpecialties();
        model.addAttribute("specialtiesList", specialtiesList);
        List<Faculty> facultyList = facultyService.getAllFaculties();
        model.addAttribute("facultyList", facultyList);

        return "editRequestsPage";
    }

    @PostMapping("user/editRecruitRequests/{recruitId}")
    public String editRecruitRequests(@PathVariable("recruitId") Long recruitId, @Valid ThreeSpecialtiesRequest tsr) {
        Specialty firstPrioritySpecialty = tsr.getFirstPriority();
        Specialty secondPrioritySpecialty = tsr.getSecondPriority();
        Specialty thirdPrioritySpecialty = tsr.getThirdPriority();

        List<Request> recruitRequests = requestService.getAllRequestsByRecruitId(recruitId);
        Optional<Request> firstPriorityRequestOpt = recruitRequests.stream().filter(req -> req.getPriority() == 1).findFirst();
        Request firstPriorityRequest = firstPriorityRequestOpt.get();
        firstPriorityRequest.setSpecialty(firstPrioritySpecialty);

        Optional<Request> secondPrioritySpecialtyOpt = recruitRequests.stream().filter(req -> req.getPriority() == 2).findFirst();
        Request secondPriorityRequest = secondPrioritySpecialtyOpt.get();
        secondPriorityRequest.setSpecialty(secondPrioritySpecialty);

        Optional<Request> thirdPrioritySpecialtyOpt = recruitRequests.stream().filter(req -> req.getPriority() == 3).findFirst();
        Request thirdPriorityRequest = thirdPrioritySpecialtyOpt.get();
        thirdPriorityRequest.setSpecialty(thirdPrioritySpecialty);

        requestService.addRequest(firstPriorityRequest);
        requestService.addRequest(secondPriorityRequest);
        requestService.addRequest(thirdPriorityRequest);

        return "redirect:/user/recruitsListPage";
    }
}
