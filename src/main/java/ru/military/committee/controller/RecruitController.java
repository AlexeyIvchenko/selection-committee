package ru.military.committee.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.military.committee.domain.location.Address;
import ru.military.committee.domain.location.City;
import ru.military.committee.domain.location.Region;
import ru.military.committee.domain.personal.Passport;
import ru.military.committee.domain.personal.Recruit;
import ru.military.committee.service.*;
import ru.military.committee.utils.DateWorker;
import ru.military.committee.utils.LogCreator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class RecruitController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private NationalityService nationalityService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private CityService cityService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CategoryService categoryService;

    private static final Logger log = Logger.getLogger(RecruitController.class);

    /**
     * Формирует данные модели и возвращает имя html-страницы с формой регистрации личного дела абитуриента.
     *
     * @param recruitForm - объект, хранящий личные данные об абитуриенте.
     * @param model       - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с формой регистрации личного дела абитуриента.
     */
    @GetMapping("/user/recruitQuestionary")
    public String showRecruitForm(@ModelAttribute("recruitForm") Recruit recruitForm, Model model) {
        model.addAttribute("categoriesList", categoryService.getAllCategories());
        model.addAttribute("nationalitiesList", nationalityService.getAllNationalities());
        model.addAttribute("regionsList", regionService.getAllRegions());
        model.addAttribute("officesList", officeService.getAllOffices());
        model.addAttribute("citiesList", cityService.getAllCities());
        model.addAttribute("message");
        return "recruitQuestionaryPage";
    }

    /**
     * Регистрирует личное дело абитуриента.
     *
     * @param recruit            - объект, хранящий личные данные об абитуриенте.
     * @param bindingResult      - объект, хранящий ошибки валидации объектов, полученных из html-страницы.
     * @param redirectAttributes - объект, хранящий атрибуты, передающиеся при перенаправлении.
     * @return - перенаправляет на html-страницу с формой регистрации личного дела абитуриента.
     */
    @PostMapping("/user/addRecruit")
    public String addRecruit(@ModelAttribute("recruitForm") @Valid Recruit recruit, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recruitForm", recruit);
            redirectAttributes.addFlashAttribute("message", "Проверьте корректность введенных данных!");
            return "redirect:/user/recruitQuestionary";
        }
        recruit.setRegistrationYear(DateWorker.getCurrentYear());
        recruitService.addRecruit(recruit);
        redirectAttributes.addFlashAttribute("message", "Личное дело абитуриента зарегистрировано!");
        return "redirect:/user/recruitQuestionary";
    }

    //Возвращает список городов определенного региона.
    @RequestMapping(value = "/user/cities", method = RequestMethod.GET)
    public @ResponseBody
    List<City> findAllCities(@RequestParam(value = "regionId", required = true) Long regionId) {
        Region region = regionService.getRegionById(regionId);
        List<City> citiesList = cityService.getAllCitiesInRegion(region);
        return citiesList;
    }

    /**
     * Формирует данные модели и возвращает имя html-страницы с таблицей зарегистрированных абитуриентов.
     *
     * @param model - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с таблицей зарегистрированныхабитуриентов.
     */
    @GetMapping(value = "/user/recruitsListPage")
    public String getRecruitsListPage(Model model) {
        List<Recruit> recruitsList = recruitService.getAllRecruits();
        Collections.sort(recruitsList, (rec1, rec2) -> rec2.getRecruitId().compareTo(rec1.getRecruitId()));
        model.addAttribute("recruitsList", recruitsList);
        return "recruitsListPage";
    }

    /**
     * Удаляет личное дело абитуриента.
     *
     * @param recruitId - уникальный идентификатор абитуриента.
     * @param principal - хранит информацию о пользователе, авторизованном в системе.
     * @return - перенаправляет на html-страницу с таблицей зарегистрированных абитуриентов.
     */
    @GetMapping(value = "/user/deleteRecruit")
    public String deleteRecruit(@RequestParam(name = "recruitId") Long recruitId, Principal principal) {
        Recruit recruit = recruitService.getRecruitById(recruitId);
        recruitService.deleteRecruitById(recruitId);
        //Запись события в log-файл
        LogCreator.logEvent(log, principal, "info", "Удалено личное дело абитериента (" + recruit.getSurname() + " " + recruit.getName() + " " + recruit.getSecondName() + ")");
        return "redirect:/user/recruitsListPage";
    }

    /**
     * Формирует данные модели и возвращает имя html-страницы с формой редактирования личного дела абитуриента.
     *
     * @param recruitId - уникальный идентификатор абитуриента.
     * @param model     - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с формой редактирования личного дела абитуриента.
     */
    @GetMapping(value = "/user/editPage/{recruitId}")
    public String getRecruitEditPage(@PathVariable("recruitId") Long recruitId, Model model) {
        Recruit recruit = recruitService.getRecruitById(recruitId);
        model.addAttribute("recruit", recruit);

        model.addAttribute("categoriesList", categoryService.getAllCategories());
        model.addAttribute("nationalitiesList", nationalityService.getAllNationalities());
        model.addAttribute("officesList", officeService.getAllOffices());
        model.addAttribute("regionsList", regionService.getAllRegions());
        model.addAttribute("citiesList", cityService.getAllCities());
        model.addAttribute("validYears", DateWorker.getValidYears());
        model.addAttribute("wrongData");

        return "editRecruitInfoPage";
    }

    /**
     * Редактирует личное дело абитуриента.
     *
     * @param recruitId          - уникальный идентификатор абитуриента.
     * @param editedRecruit      - объект, хранящий отредактированные данные абитуриента.
     * @param bindingResult      - объект, хранящий ошибки валидации объектов, полученных из html-страницы.
     * @param redirectAttributes - объект, хранящий атрибуты, передающиеся при перенаправлении.
     * @param principal          - хранит информацию о пользователе, авторизованном в системе.
     * @return - перенаправляет на html-страницу с таблицей зарегистрированных абитуриентов.
     */
    @PostMapping("/user/editRecruit/{recruitId}")
    public String editRecruitInfo(@PathVariable("recruitId") Long recruitId, @Valid Recruit editedRecruit, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("wrongData", "Измененные данные были введены некорректно!");
            return "redirect:/user/editPage/" + recruitId;
        }
        Recruit recruitFromBase = recruitService.getRecruitById(recruitId);

        Passport editedPassport = editedRecruit.getPassport();
        editedPassport.setPassportId(recruitFromBase.getPassport().getPassportId());
        passportService.addPassport(editedPassport);

        Address editedAddress = editedRecruit.getAddress();
        editedAddress.setAddressId(recruitFromBase.getAddress().getAddressId());
        addressService.addAddress(editedAddress);

        editedRecruit.setExam(recruitFromBase.getExam());
        editedRecruit.setCertificate(recruitFromBase.getCertificate());
        editedRecruit.setRequests(recruitFromBase.getRequests());
        editedRecruit.setExtranceTest(recruitFromBase.getExtranceTest());
        editedRecruit.setPlatoon(recruitFromBase.getPlatoon());

        recruitService.addRecruit(editedRecruit);
        //Запись события в log-файл
        Recruit recruit = recruitService.getRecruitById(recruitId);
        LogCreator.logEvent(log, principal, "info", "Изменено личное дело абитериента (" + recruit.getSurname() + " " + recruit.getName() + " " + recruit.getSecondName());
        return "redirect:/user/recruitsListPage";
    }
}
