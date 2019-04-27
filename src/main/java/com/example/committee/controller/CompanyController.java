package com.example.committee.controller;

import com.example.committee.domain.personal.Company;
import com.example.committee.domain.personal.Platoon;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
import com.example.committee.service.*;
import com.example.committee.utils.CompanyCreateHelper;
import com.example.committee.utils.DateWorker;
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
    @Autowired
    private PlatoonService platoonService;

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
        //добавляем роты для выбранного факультета
        createCompaniesByFaculty(companyCreateHelper.getCompaniesCount(), facultyId);
        //добавляем взвода для выбранного факультета
        createPlatoonsByFacultyAndYear(facultyId, (short) DateWorker.getCurrentYear());
        //распределям абитуриентов по взводам
        distributeRecruits(facultyId, (short) DateWorker.getCurrentYear());
        return "redirect:/user/facultiesPage";
    }

    private void createCompaniesByFaculty(short companiesCount, short facultyId) {
        Faculty selectedFaculty = facultyService.getFacultyById(facultyId);
        for (int i = 0; i < companiesCount; i++) {
            Company company = new Company();
            company.setCompanyNumber((short) (i + 1));
            company.setOwnerFaculty(selectedFaculty);
            company.setCreateYear((short) DateWorker.getCurrentYear());
            companyService.addCompany(company);
        }
    }

    private void createPlatoonsByFacultyAndYear(short facultyId, short year) {
        List<Company> companies = companyService.getCompaniesByFacultyAndYear(facultyId, year);

        for (int i = 0; i < companies.size(); i++) {
            for (int j = 0; j < 3; j++) {
                Platoon platoon = new Platoon();
                platoon.setPlatoonNumber((short) (j + 1));
                platoon.setCompany(companies.get(i));
                platoonService.addPlatoon(platoon);
            }
        }
    }

    private void distributeRecruits(short facultyId, short year) {
        List<Request> requests = requestService.getRequestsByPriorityAndSpecialty_Faculty_FacultyId((short) 1, facultyId);
        //Получаем список абитуриентов с 1 приоритетом на выбранный факультет
        List<Recruit> recruitsList = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            recruitsList.add(requests.get(i).getRecruit());
        }
        List<Company> companies = companyService.getCompaniesByFacultyAndYear(facultyId, year);
        List<Platoon> platoons = platoonService.getPlatoonsByCompaniesList(companies);
        int j = 0;
        for (int i = 0; i < recruitsList.size(); i++) {
            recruitsList.get(i).setPlatoon(platoons.get(j));
            recruitService.addRecruit(recruitsList.get(i));
            if (j == platoons.size() - 1) {
                j = 0;
            } else {
                j++;
            }
        }
    }


}
