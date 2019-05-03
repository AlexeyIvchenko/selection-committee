package com.example.committee.controller;

import com.example.committee.domain.location.Address;
import com.example.committee.domain.location.City;
import com.example.committee.domain.location.Office;
import com.example.committee.domain.location.Region;
import com.example.committee.domain.personal.*;
import com.example.committee.service.*;
import com.example.committee.utils.CascadingSelectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private RequestService requestService;

    @GetMapping("/user/recruitQuestionary")
    public String showRecruitForm(@ModelAttribute("recruitForm") Recruit recruitForm, Model model) {
        List<Nationality> nationalitiesList = nationalityService.getAllNationalities();
        List<Region> regionsList = regionService.getAllRegions();
        List<Office> officesList = officeService.getAllOffices();
        List<City> citiesList = cityService.getAllCities();
        //List<Integer> examYearsList = DateWorker.getValidExamYears();

        CascadingSelectHelper cascadingSelectHelper = new CascadingSelectHelper();
        model.addAttribute("cascadingSelectHelper", cascadingSelectHelper);

        model.addAttribute("nationalitiesList", nationalitiesList);
        model.addAttribute("regionsList", regionsList);
        model.addAttribute("officesList", officesList);
        model.addAttribute("citiesList", citiesList);
        //model.addAttribute("validExamYears", examYearsList);

        return "recruitQuestionaryPage";
    }

    @PostMapping("/user/addRecruit")
    public String addRecruit(@ModelAttribute("recruitForm") Recruit recruit) {
        recruitService.addRecruit(recruit);
        return "redirect:/user/recruitQuestionary";
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public @ResponseBody
    List<City> findAllCities(@RequestParam(value = "regionId", required = true) Long regionId) {
        Region region = regionService.getRegionById(regionId);
        List<City> citiesList = cityService.getAllCitiesInRegion(region);
        return citiesList;
    }

    @GetMapping(value = "/user/recruitsListPage")
    public String getRecruitsListPage(Model model) {
        List<Recruit> recruitsList = recruitService.getAllRecruits();
        Collections.sort(recruitsList, (rec1, rec2) -> rec2.getRecruitId().compareTo(rec1.getRecruitId()));
        model.addAttribute("recruitsList", recruitsList);
        return "recruitsListPage";
    }

    @GetMapping(value = "/user/deleteRecruit")
    public String deleteRecruit(@RequestParam(name = "recruitId") Long recruitId) {
        recruitService.deleteRecruitById(recruitId);
        return "redirect:/user/recruitsListPage";
    }

    @GetMapping(value = "/user/editPage/{recruitId}")
    public String getRecruitEditPage(@PathVariable("recruitId") Long recruitId, Model model) {
        Recruit recruit = recruitService.getRecruitById(recruitId);
        model.addAttribute("recruit", recruit);

        List<Nationality> nationalitiesList = nationalityService.getAllNationalities();
        List<Office> officesList = officeService.getAllOffices();
        List<City> citiesList = cityService.getAllCities();

        model.addAttribute("nationalitiesList", nationalitiesList);
        model.addAttribute("officesList", officesList);
        model.addAttribute("citiesList", citiesList);

        return "editInfoPage";
    }

    @PostMapping("/user/editRecruit/{recruitId}")
    public String editRecruitInfo(@PathVariable("recruitId") Long recruitId, @Valid Recruit editedRecruit) {
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
        return "redirect:/user/recruitsListPage";
    }
}
