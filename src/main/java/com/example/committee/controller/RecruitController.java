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
    @Autowired
    private ExamService examService;
    @Autowired
    private CertificateService certificateService;

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
        recruit.setExam(new Exam(recruit.getRecruitId()));
        recruit.setCertificate(new Certificate(recruit.getRecruitId()));
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
        Passport editedPassport = recruit.getPassport();
        editedPassport.setPassportId(recruitId);
        passportService.addPassport(editedPassport);

        Address editedAddress = recruit.getAddress();
        editedAddress.setAddressId(recruitId);
        addressService.addAddress(editedAddress);

        Exam exam = examService.getExamByRecruitId(recruitId);
        recruit.setExam(exam);
        Certificate certificate = certificateService.getCertificateByRecruitId(recruitId);
        recruit.setCertificate(certificate);

        recruitService.addRecruit(recruit);
        return "redirect:/user/recruitsListPage";
    }

    @GetMapping(value = "/user/recruitEducationPage/{recruitId}")
    public String getRecruitEducationPage(@PathVariable("recruitId") Long recruitId, Model model) {
        Exam exam = examService.getExamByRecruitId(recruitId);
        model.addAttribute("exam", exam);

        Certificate certificate = certificateService.getCertificateByRecruitId(recruitId);
        model.addAttribute("certificate", certificate);

        model.addAttribute("recruitId", recruitId);

        return "recruitEducationPage";
    }

    @PostMapping("/user/editExam/{recruitId}")
    public String editEducationInfo(@PathVariable("recruitId") Long recruitId, @Valid Exam exam, Model model) {
        exam.setExamId(examService.getExamByRecruitId(recruitId).getExamId());
        examService.addExam(exam);
        return "redirect:/user/recruitsListPage";
    }

    @PostMapping("/user/editCertificate/{recruitId}")
    public String editEducationInfo(@PathVariable("recruitId") Long recruitId, @Valid Certificate certificate, Model model) {
        certificate.setCertificateId(certificateService.getCertificateByRecruitId(recruitId).getCertificateId());
        certificateService.addCertificate(certificate);
        return "redirect:/user/recruitsListPage";
    }
}
