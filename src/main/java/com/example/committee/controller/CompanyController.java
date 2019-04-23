package com.example.committee.controller;

import com.example.committee.domain.personal.Company;
import com.example.committee.domain.personal.Platoon;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
import com.example.committee.service.*;
import com.example.committee.utils.CompanyCreateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CompanyController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/user/facultiesPage")
    public String getFacultiesPage(Model model) {
        List<Faculty> facultiesList = facultyService.getAllFaculties();
        model.addAttribute("facultiesList", facultiesList);

        return "facultiesPage";
    }

    @GetMapping(value = "/user/companyCreatePage/{facultyId}")
    public String getCompanyCreatePage(@ModelAttribute("companyCreateHelper") CompanyCreateHelper companyCreateHelper, @PathVariable("facultyId") short facultyId, Model model) {
        List<Faculty> facultiesList = facultyService.getAllFaculties();
        model.addAttribute("facultiesList", facultiesList);
        model.addAttribute("facultyId", facultyId);
        String message = createRequestsCountMessage(facultyId);
        model.addAttribute("requestsCountMessage", message);

        return "companyCreatePage";
    }

    private String createRequestsCountMessage(short facultyId) {
        List<Request> requests = requestService.getRequestsByPriorityAndSpecialty_Faculty_FacultyId((short) 1, facultyId);
        Faculty currentFaculty = facultyService.getFacultyById(facultyId);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("На факультет '").append(currentFaculty.getFacultyName()).append("' по первому приоритету подали ").append(requests.size()).append(" заявок");
        return stringBuilder.toString();
    }

    @PostMapping("/user/createCompanies/{facultyId}")
    public String createCompaniesInFaculty(@ModelAttribute("companyCreateHelper") CompanyCreateHelper companyCreateHelper, @PathVariable("facultyId") short facultyId) {
        System.out.println("формируем " + companyCreateHelper.getCompaniesCount() + " роты");
        List<Request> requests = requestService.getRequestsByPriorityAndSpecialty_Faculty_FacultyId((short) 1, facultyId);
        //Получаем список абитуриентов с 1 приоритетом на факультет
        List<Recruit> recruitsList = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            recruitsList.add(requests.get(i).getRecruit());
        }
        //TODO: Добавить факультет и год в роту, чтобы можно было доставать взвода по ротам.

        return "redirect:/user/facultiesPage";
    }


}
