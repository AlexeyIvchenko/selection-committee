package com.example.committee.controller;

import com.example.committee.domain.personal.Certificate;
import com.example.committee.domain.personal.Exam;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.service.CertificateService;
import com.example.committee.service.ExamService;
import com.example.committee.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EducationController {
    @Autowired
    private RecruitService recruitService;
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
        } else {
            selectedRecruit.setExam(exam);
            exam.setRecruit(selectedRecruit);
        }
        examService.addExam(exam);
        return "redirect:/user/recruitsListPage";
    }

    @PostMapping("/user/addCertificate/{recruitId}")
    public String addCertificate(@ModelAttribute("certificate") Certificate certificate, @PathVariable("recruitId") Long recruitId) {
        Recruit selectedRecruit = recruitService.findById(recruitId);
        if (selectedRecruit.getCertificate() != null) {
            certificate.setCertificateId(selectedRecruit.getCertificate().getCertificateId());
        } else {
            selectedRecruit.setCertificate(certificate);
            certificate.setRecruit(selectedRecruit);
        }
        certificateService.addCertificate(certificate);
        return "redirect:/user/recruitsListPage";
    }
}
