package com.example.committee.controller;

import com.example.committee.domain.location.Address;
import com.example.committee.domain.location.City;
import com.example.committee.domain.location.Office;
import com.example.committee.domain.location.Region;
import com.example.committee.domain.personal.Nationality;
import com.example.committee.domain.personal.Passport;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.service.*;
import com.example.committee.utils.CascadingSelectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/user/recruitQuestionary")
    public String showRecruitForm(@ModelAttribute("recruitForm") Recruit recruitForm, @ModelAttribute("selectRegion") Region region, @ModelAttribute("selectCity") City city, Model model) {
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

        return "recruitQuestionary";
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
        Recruit recruit = recruitService.findById(recruitId);
        model.addAttribute("recruit", recruit);

        List<Nationality> nationalitiesList = nationalityService.getAllNationalities();
        List<Office> officesList = officeService.getAllOffices();
        List<City> citiesList = cityService.getAllCities();

        model.addAttribute("nationalitiesList", nationalitiesList);
        model.addAttribute("officesList", officesList);
        model.addAttribute("citiesList", citiesList);

        return "recruitInfoEditPage";
    }

    @PostMapping("user/editRecruit/{recruitId}")
    public String editRecruitInfo(@PathVariable("recruitId") Long recruitId, @Valid Recruit recruit, BindingResult result, Model model) {
        if (result.hasErrors()) {
            recruit.setRecruitId(recruitId);
            return "recruitInfoEditPage";
        }
        Passport passport = passportService.getPassportByRecruitId(recruitId);
        passport.setPassportNumber(recruit.getPassport().getPassportNumber());
        passport.setPassportIssuedBy(recruit.getPassport().getPassportIssuedBy());
        passport.setPassportDate(recruit.getPassport().getPassportDate());
        recruit.setPassport(passport);

        Address address = addressService.getAddressByRecruitId(recruitId);
        address.setCity(recruit.getAddress().getCity());
        address.setStreetName(recruit.getAddress().getStreetName());
        address.setVillageName(recruit.getAddress().getVillageName());
        address.setHouseNumber(recruit.getAddress().getHouseNumber());
        address.setBlockNumber(recruit.getAddress().getBlockNumber());
        address.setApartmentNumber(recruit.getAddress().getApartmentNumber());
        recruit.setAddress(address);

        recruitService.addRecruit(recruit);
        return "redirect:/user/recruitsListPage";
    }


}
