package com.example.committee.controller;

import com.example.committee.domain.personal.Certificate;
import com.example.committee.domain.personal.Exam;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
import com.example.committee.domain.request.Specialty;
import com.example.committee.service.*;
import com.example.committee.utils.CascadingSelectHelper;
import com.example.committee.utils.DateWorker;
import com.example.committee.utils.ThreeSpecialtiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class RecruitRequestController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SpecialtyService specialtyService;


    @GetMapping(value = "/user/requestsPage/{recruitId}")
    public String getRequestsPage(@ModelAttribute("tsr") ThreeSpecialtiesRequest threeFacultiesRequest, @PathVariable("recruitId") Long recruitId, Model model) {

        List<Faculty> facultyList = facultyService.getAllFaculties();
        model.addAttribute("facultyList", facultyList);

        model.addAttribute("recruitId", recruitId);

        return "requestsPage";
    }

    @PostMapping("/user/addRequests")
    public String addRequests(@ModelAttribute("tfr") ThreeSpecialtiesRequest threeSpecialtiesRequest, @RequestParam(value = "recruitId") Long recruitId) {
        Recruit trueRecruit = recruitService.findById(recruitId);
        Specialty firstPrioritySpecialty = threeSpecialtiesRequest.getFirstPriority();
        Specialty secondPrioritySpecialty = threeSpecialtiesRequest.getSecondPriority();
        Specialty thirdPrioritySpecialty = threeSpecialtiesRequest.getThirdPriority();

        Date currentDate = DateWorker.getCurrentDate();
        Request firstPriorityRequest = new Request(trueRecruit, firstPrioritySpecialty, (short) 1, currentDate);
        Request secondPriorityRequest = new Request(trueRecruit, secondPrioritySpecialty, (short) 2, currentDate);
        Request thirdPriorityRequest = new Request(trueRecruit, thirdPrioritySpecialty, (short) 2, currentDate);

        requestService.addRequest(firstPriorityRequest);
        requestService.addRequest(secondPriorityRequest);
        requestService.addRequest(thirdPriorityRequest);
        return "redirect:/user/recruitsListPage";
    }

    @RequestMapping(value = "/user/specialties", method = RequestMethod.GET)
    public @ResponseBody
    List<Specialty> findSpecialtiesByFaculty(@RequestParam(value = "facultyId", required = true) short facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        List<Specialty> specialtiesList = specialtyService.getAllSpecialtiesInFaculty(faculty);
        return specialtiesList;
    }
}
