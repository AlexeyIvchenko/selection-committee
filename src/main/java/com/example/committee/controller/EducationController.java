package com.example.committee.controller;

import com.example.committee.domain.personal.Certificate;
import com.example.committee.domain.personal.Exam;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class EducationController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private ExamService examService;
    @Autowired
    private CertificateService certificateService;

    @GetMapping("/user/educationPage/{recruitId}")
    public String getEducationPage(@ModelAttribute("exam") Exam exam, @ModelAttribute("certificate") Certificate certificate, @PathVariable("recruitId") Long recruitId, Model model) {
        Recruit selectedRecruit = recruitService.findById(recruitId);
        if (selectedRecruit.getExam() != null) {
            model.addAttribute("exam", selectedRecruit.getExam());
            model.addAttribute("action", "edit");
        }
        if (selectedRecruit.getCertificate() != null) {
            model.addAttribute("certificate", selectedRecruit.getCertificate());
        }
        model.addAttribute("recruitId", recruitId);
        return "educationPage";
    }

    @PostMapping("/user/addExam/{recruitId}")
    public String addExam(@ModelAttribute("exam") Exam exam, @PathVariable("recruitId") Long recruitId) {
        Recruit selectedRecruit = recruitService.findById(recruitId);
        if (selectedRecruit.getExam() != null) {
            exam.setExamId(selectedRecruit.getExam().getExamId());
            examService.addExam(exam);
        } else {
            selectedRecruit.setExam(exam);
            exam.setRecruit(selectedRecruit);
            examService.addExam(exam);
        }

        return "redirect:/user/recruitsListPage";
    }

    @PostMapping("/user/addCertificate/{recruitId}")
    public String addCertificate(@ModelAttribute("certificate") Certificate certificate, @PathVariable("recruitId") Long recruitId) {
        Recruit selectedRecruit = recruitService.findById(recruitId);
        selectedRecruit.setCertificate(certificate);
        certificate.setRecruit(selectedRecruit);
        certificateService.addCertificate(certificate);
        return "redirect:/user/recruitsListPage";
    }

    @GetMapping(value = "/user/editEducationPage/{recruitId}")
    public String getEditEducationPage(@PathVariable("recruitId") Long recruitId, Model model) {
        Exam exam = examService.getExamByRecruitId(recruitId);
        model.addAttribute("exam", exam);

        Certificate certificate = certificateService.getCertificateByRecruitId(recruitId);
        model.addAttribute("certificate", certificate);

        model.addAttribute("recruitId", recruitId);

        return "editEducationPage";
    }

    @PostMapping("/user/editExam/{recruitId}")
    public String editEducationInfo(@PathVariable("recruitId") Long recruitId, @ModelAttribute("exam") Exam exam) {
        exam.setExamId(examService.getExamByRecruitId(recruitId).getExamId());
        examService.addExam(exam);
        return "redirect:/user/recruitsListPage";
    }

    @PostMapping("/user/editCertificate/{recruitId}")
    public String editEducationInfo(@PathVariable("recruitId") Long recruitId, @ModelAttribute("certificate") Certificate certificate) {
        certificate.setCertificateId(certificateService.getCertificateByRecruitId(recruitId).getCertificateId());
        certificateService.addCertificate(certificate);
        return "redirect:/user/recruitsListPage";
    }


}
