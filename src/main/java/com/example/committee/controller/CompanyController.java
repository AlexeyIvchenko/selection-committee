package com.example.committee.controller;

import com.example.committee.domain.personal.Company;
import com.example.committee.domain.personal.Platoon;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
import com.example.committee.service.*;
import com.example.committee.utils.CascadingSelectHelper;
import com.example.committee.utils.CompanyCreateHelper;
import com.example.committee.utils.DateWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(value = "/user/recruitsDistributionPage")
    public String getRecruitsDistributionPage(Model model) {
        List<Company> companies = companyService.getCompaniesByFacultyAndYear((short) 1, (short) DateWorker.getCurrentYear());
        List<Platoon> platoons = platoonService.getPlatoonsByCompaniesList(companies);
        List<Recruit> recruits = recruitService.getRecruitsByPlatoonsList(platoons);
        model.addAttribute("recruitsList", recruits);

        List<Faculty> facultyList = facultyService.getAllFaculties();
        model.addAttribute("facultyList", facultyList);
        return "recruitsDistributionPage";
    }

    @PostMapping(value = "/user/showDistributionByFaculty")
    public String showDistributionByFaculty(@RequestParam(value = "faculty") short facultyId, Model model) {
        List<Company> companies = companyService.getCompaniesByFacultyAndYear(facultyId, (short) DateWorker.getCurrentYear());
        List<Platoon> platoons = platoonService.getPlatoonsByCompaniesList(companies);
        List<Recruit> recruits = recruitService.getRecruitsByPlatoonsList(platoons);
        model.addAttribute("recruitsList", recruits);

        List<Faculty> facultyList = facultyService.getAllFaculties();
        model.addAttribute("facultyList", facultyList);
        return "recruitsDistributionPage";
    }

    @GetMapping(value = "/user/moveRecruitPage/{recruitId}")
    public String getMoveRecruitPage(@PathVariable("recruitId") Long recruitId, Model model) {
        Request request = requestService.getRequestByRecruitIdAndPriority(recruitId, (short) 1);
        Faculty faculty = request.getSpecialty().getFaculty();

        List<Company> companies = companyService.getCompaniesByFacultyAndYear(faculty.getFacultyId(), (short) DateWorker.getCurrentYear());
        model.addAttribute("companies", companies);

        Recruit recruit = recruitService.getRecruitById(recruitId);
        Platoon platoon = recruit.getPlatoon();
        Company company = platoon.getCompany();
        List<Platoon> platoons = platoonService.getPlatoonsByCompany(company);
        model.addAttribute("platoonList", platoons);

        CascadingSelectHelper cascadingSelectHelper = new CascadingSelectHelper();
        cascadingSelectHelper.setRecruitPlatoon(platoon);
        cascadingSelectHelper.setCompany(company);
        model.addAttribute("cascadingSelectHelper", cascadingSelectHelper);

        model.addAttribute("recruitId", recruitId);

        return "moveRecruitPage";
    }

    @RequestMapping(value = "/user/platoons", method = RequestMethod.GET)
    public @ResponseBody
    List<Platoon> getPlatoonsByCompany(@RequestParam(value = "companyId", required = true) Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        List<Platoon> platoons = platoonService.getPlatoonsByCompany(company);
        return platoons;
    }

    @PostMapping("user/moveRecruit/{recruitId}")
    public String moveRecruit(@PathVariable("recruitId") Long recruitId, @Valid CascadingSelectHelper csh) {
        Platoon platoon = csh.getRecruitPlatoon();
        Recruit recruit = recruitService.getRecruitById(recruitId);
        recruit.setPlatoon(platoon);
        recruitService.addRecruit(recruit);

        return "redirect:/user/recruitsDistributionPage";
    }

}