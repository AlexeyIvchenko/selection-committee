package com.example.committee.controller;

import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
import com.example.committee.domain.request.Specialty;
import com.example.committee.service.FacultyService;
import com.example.committee.service.RecruitService;
import com.example.committee.service.RequestService;
import com.example.committee.service.SpecialtyService;
import com.example.committee.utils.DateWorker;
import com.example.committee.utils.ThreeSpecialtiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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

        Faculty faculty = facultyService.getFacultyById((short) 1);
        List<Specialty> firstFacultySpecialtiesList = specialtyService.getAllSpecialtiesInFaculty(faculty);
        model.addAttribute("firstFacultySpecialtiesList", firstFacultySpecialtiesList);

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
        Request thirdPriorityRequest = new Request(trueRecruit, thirdPrioritySpecialty, (short) 3, currentDate);

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
