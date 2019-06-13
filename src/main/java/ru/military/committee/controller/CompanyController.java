package ru.military.committee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.military.committee.domain.personal.Company;
import ru.military.committee.domain.personal.Platoon;
import ru.military.committee.domain.personal.Recruit;
import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.Request;
import ru.military.committee.service.*;
import ru.military.committee.utils.CascadingSelectHelper;
import ru.military.committee.utils.DateWorker;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для формирования рот из абитуриентов, перемещение абитуриентов между взводами.
 */
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

    /**
     * Формирует данные модели и возвращает имя html-страницы с таблицей факультетов.
     *
     * @param model - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с таблицей факультетов.
     */
    @GetMapping(value = "/user/facultiesPage")
    public String getFacultiesPage(Model model) {
        List<Faculty> facultiesList = facultyService.getAllFaculties();
        model.addAttribute("facultiesList", facultiesList);
        return "facultiesPage";
    }

    /**
     * Формирует данные модели и возвращает имя html-страницы с формированием рот из абитуриентов.
     *
     * @param facultyId - уникальный идентификатор факультета.
     * @param model     - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с таблицей факультетов.
     */
    @GetMapping(value = "/user/companyCreatePage/{facultyId}")
    public String getCompanyCreatePage(@PathVariable("facultyId") short facultyId, Model model) {
        model.addAttribute("facultiesList", facultyService.getAllFaculties());
        model.addAttribute("facultyId", facultyId);
        model.addAttribute("requestsCountMessage", createRequestsCountMessage(facultyId));
        return "companyCreatePage";
    }

    /**
     * Формирует сообщение с информацией о количестве заявлений, поданных по первому приоритету на факультет с выбранным id.
     *
     * @param facultyId - уникальный идентификатор факультета.
     * @return - сформированное сообщение.
     */
    private String createRequestsCountMessage(short facultyId) {
        List<Request> requests = requestService.getRequestsByPriorityAndSpecialty_Faculty_FacultyId((short) 1, facultyId);
        Faculty currentFaculty = facultyService.getFacultyById(facultyId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("На факультет '").append(currentFaculty.getFacultyName()).append("' по первому приоритету подали ").append(requests.size()).append(" заявлений");
        return stringBuilder.toString();
    }

    /**
     * Формирует роты и взвода из абитуриентов исходя из их заявлений по первым приоритетам.
     *
     * @param companiesCount - количество формируемых рот.
     * @param facultyId      - уникальный идентификатор факультета.
     * @return - перенаправляет на html-страницу с таблицей факультетов.
     */
    @PostMapping("/user/createCompanies/{facultyId}")
    public String createCompaniesInFaculty(@RequestParam(name = "companiesCount") short companiesCount, @PathVariable("facultyId") short facultyId) {
        //добавляем роты для выбранного факультета
        createCompaniesByFaculty(companiesCount, facultyId);
        //добавляем взвода для выбранного факультета
        createPlatoonsByFacultyAndYear(facultyId, (short) DateWorker.getCurrentYear());
        //распределям абитуриентов по взводам
        distributeRecruits(facultyId, (short) DateWorker.getCurrentYear());
        return "redirect:/user/facultiesPage";
    }

    /**
     * Создает роты по выбранному факультету.
     *
     * @param companiesCount - количество формируемых рот.
     * @param facultyId      - унивкальный идентификатор факультета.
     */
    private void createCompaniesByFaculty(short companiesCount, short facultyId) {
        Faculty selectedFaculty = facultyService.getFacultyById(facultyId);
        for (int i = 0; i < companiesCount; i++) {
            Company company = new Company();
            company.setCompanyNumber((short) (i + 1));
            company.setOwnerFaculty(selectedFaculty);
            company.setCreateYear(DateWorker.getCurrentYear());
            companyService.addCompany(company);
        }
    }

    /**
     * Создает взвода в ротах.
     *
     * @param facultyId - уникальный идентификатор факультета.
     * @param year      - год формирования роты.
     */
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

    /**
     * Распределяет абитуриентов по взводам.
     *
     * @param facultyId - уникальный идентификатор факультета.
     * @param year      - год формирования роты.
     */
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
            //помещаем абитуриента во взвод
            recruitsList.get(i).setPlatoon(platoons.get(j));
            recruitService.addRecruit(recruitsList.get(i));
            if (j == platoons.size() - 1) {
                j = 0;
            } else {
                j++;
            }
        }
    }

    /**
     * Формирует данные модели и возвращает имя html-страницы с личными составами рот и взводов.
     *
     * @param model - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с личными составами рот и взводов.
     */
    @GetMapping(value = "/user/recruitsDistributionPage")
    public String getRecruitsDistributionPage(Model model) {
        List<Company> companies = companyService.getCompaniesByFacultyAndYear((short) 1, (short) DateWorker.getCurrentYear());
        List<Platoon> platoons = platoonService.getPlatoonsByCompaniesList(companies);
        List<Recruit> recruits = recruitService.getRecruitsByPlatoonsList(platoons);
        model.addAttribute("recruitsList", recruits);
        model.addAttribute("facultyList", facultyService.getAllFaculties());
        return "recruitsDistributionPage";
    }

    /**
     * Формирует данные модели и возвращает имя html-страницы с личным составом рот по конкретному факультету.
     *
     * @param facultyId - уникальный идентификатор факультета.
     * @param model     - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с личным составом рот по конкретному факультету.
     */
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

    /**
     * Формирует данные модели и возвращает имя html-страницы с информацией о том, к какой роте и взводу относится абитуриент.
     *
     * @param recruitId - уникальный идентификатор абитуриента.
     * @param model     - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с информацией о том, к какой роте и взводу относится абитуриент.
     */
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

    //Возвращает список взводов по уникальному индетификатору роты
    @RequestMapping(value = "/user/platoons", method = RequestMethod.GET)
    public @ResponseBody
    List<Platoon> getPlatoonsByCompany(@RequestParam(value = "companyId") Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        List<Platoon> platoons = platoonService.getPlatoonsByCompany(company);
        return platoons;
    }

    /**
     * Перемещает абитуриента в выбранную роту или взвод.
     *
     * @param recruitId - уникальный идентификатор абитуриента.
     * @param csh       - вспомогательный объект для реализации каскадных выпадающих списков.
     * @return - перенаправляет на страницу с личными составами рот.
     */
    @PostMapping("user/moveRecruit/{recruitId}")
    public String moveRecruit(@PathVariable("recruitId") Long recruitId, CascadingSelectHelper csh) {
        Platoon platoon = csh.getRecruitPlatoon();
        Recruit recruit = recruitService.getRecruitById(recruitId);
        recruit.setPlatoon(platoon);
        recruitService.addRecruit(recruit);
        return "redirect:/user/recruitsDistributionPage";
    }

}