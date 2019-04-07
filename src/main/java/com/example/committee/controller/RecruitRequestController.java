package com.example.committee.controller;

import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.MilitaryEducation;
import com.example.committee.domain.request.Request;
import com.example.committee.service.FacultyService;
import com.example.committee.service.MilitaryEducationService;
import com.example.committee.service.RecruitService;
import com.example.committee.service.RequestService;
import com.example.committee.utils.CascadingSelectHelper;
import com.example.committee.utils.ThreeFacultiesRequest;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class RecruitRequestController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private MilitaryEducationService militaryEducationService;
    @Autowired
    private FacultyService facultyService;


    @GetMapping(value = "/user/requestsPage/{recruitId}")
    public String getRequestsPage(@ModelAttribute("tfr") ThreeFacultiesRequest threeFacultiesRequest, @PathVariable("recruitId") Long recruitId, Model model) {
        List<MilitaryEducation> militaryEducationList = militaryEducationService.getAllMilitaryEducations();
        model.addAttribute("militaryEducationList", militaryEducationList);
        List<Faculty> facultyList = facultyService.getAllFaculties();
        model.addAttribute("facultyList", facultyList);

        CascadingSelectHelper cascadingSelectHelper = new CascadingSelectHelper();
        model.addAttribute("cascadingSelectHelper", cascadingSelectHelper);

        model.addAttribute("recruitId", recruitId);

        return "requestsPage";
    }

    @PostMapping("user/addRequests/{recruitId}")
    public String addRequests(@ModelAttribute("tfr") ThreeFacultiesRequest threeFacultiesRequest, @PathVariable("recruitId") Long recruitId) {
        Recruit trueRecruit = recruitService.findById(recruitId);
        Request firstPriorityRequest = threeFacultiesRequest.getFirstPriority();
        Request secondPriorityRequest = threeFacultiesRequest.getSecondPriority();
        Request thirdPriorityRequest = threeFacultiesRequest.getThirdPriority();
        firstPriorityRequest.setRecruit(trueRecruit);
        secondPriorityRequest.setRecruit(trueRecruit);
        thirdPriorityRequest.setRecruit(trueRecruit);

        requestService.addRequest(firstPriorityRequest);
        requestService.addRequest(secondPriorityRequest);
        requestService.addRequest(thirdPriorityRequest);
        return "redirect:/user/recruitsListPage";
    }

    @RequestMapping(value = "/user/faculties", method = RequestMethod.GET)
    public @ResponseBody
    List<Faculty> findAllFaculties(@RequestParam(value = "educationId", required = true) short educationId) {
        MilitaryEducation education = militaryEducationService.getMilitaryEducationById(educationId);
        List<Faculty> facultyList = facultyService.getAllFacultiesInMilitaryEducation(education);
        return facultyList;
    }
}
