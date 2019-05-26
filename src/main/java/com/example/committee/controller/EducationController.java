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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        if (selectedRecruit.getExam() != null) {
            model.addAttribute("exam", selectedRecruit.getExam());
        }
        if (selectedRecruit.getCertificate() != null) {
            model.addAttribute("certificate", selectedRecruit.getCertificate());
        }
        model.addAttribute("recruitId", recruitId);
        model.addAttribute("examMessage");
        model.addAttribute("certificateMessage");

        return "educationPage";
    }

    @PostMapping("/user/addExam/{recruitId}")
    public String addExam(@ModelAttribute("exam") @Valid Exam exam, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("recruitId") Long recruitId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("examMessage", "Проверьте корректность введенных данных о результатах ЕГЭ!");
            return "redirect:/user/educationPage/" + recruitId;
        }
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        if (selectedRecruit.getExam() != null) {
            exam.setExamId(selectedRecruit.getExam().getExamId());
            redirectAttributes.addFlashAttribute("examMessage", "Данные успешно изменены!");
        } else {
            selectedRecruit.setExam(exam);
            exam.setRecruit(selectedRecruit);
            redirectAttributes.addFlashAttribute("examMessage", "Результаты ЕГЭ успешно добавлены!");
        }
        examService.addExam(exam);
        return "redirect:/user/educationPage/" + recruitId;
    }

    @PostMapping("/user/addCertificate/{recruitId}")
    public String addCertificate(@ModelAttribute("certificate") @Valid Certificate certificate, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("recruitId") Long recruitId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("certificateMessage", "Проверьте корректность введенных данных сертификата!");
            return "redirect:/user/educationPage/" + recruitId;
        }
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        if (selectedRecruit.getCertificate() != null) {
            certificate.setCertificateId(selectedRecruit.getCertificate().getCertificateId());
            redirectAttributes.addFlashAttribute("certificateMessage", "Данные успешно изменены!");
        } else {
            selectedRecruit.setCertificate(certificate);
            certificate.setRecruit(selectedRecruit);
            redirectAttributes.addFlashAttribute("certificateMessage", "Данные сертификата успешно добавлены!");
        }
        certificateService.addCertificate(certificate);
        return "redirect:/user/educationPage/" + recruitId;
    }
}
