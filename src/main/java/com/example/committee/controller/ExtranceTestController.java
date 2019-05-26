package com.example.committee.controller;

import com.example.committee.domain.personal.ExtranceTest;
import com.example.committee.domain.personal.Recruit;
import com.example.committee.service.*;
import com.example.committee.utils.ExtranceTestHelper;
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
public class ExtranceTestController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private ExtranceTestService extranceTestService;
    @Autowired
    private HBStandartService hbStandartService;
    @Autowired
    private Run100StandartService run100StandartService;
    @Autowired
    private Run3StandartService run3StandartService;

    @GetMapping("/user/extranceTestPage/{recruitId}")
    public String getExtranceTestPage(@ModelAttribute("extranceTestHelper") ExtranceTestHelper extranceTestHelper, @PathVariable("recruitId") Long recruitId, Model model) {
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        ExtranceTest recruitExtranceTest = selectedRecruit.getExtranceTest();
        if (recruitExtranceTest != null) {
            ExtranceTestHelper eth = new ExtranceTestHelper();
            byte hbResult = hbStandartService.getResultByScore(recruitExtranceTest.getHorizontal_bar());
            double run100Result = run100StandartService.getResultByScore(recruitExtranceTest.getRun100m());
            double run3Result = run3StandartService.getResultByScore(recruitExtranceTest.getRun3km());
            byte prof_group = recruitExtranceTest.getProf_group();

            eth.setHbResult(hbResult);
            eth.setRun100mResult(run100Result);
            eth.setRun3kmResult(run3Result);
            eth.setProf_group(prof_group);
            model.addAttribute("extranceTestHelper", eth);
        }
        model.addAttribute("recruitId", recruitId);
        model.addAttribute("extranceTestMessage");
        return "extranceTestPage";
    }

    @PostMapping("/user/addExtranceTest/{recruitId}")
    public String addExtranceTest(@ModelAttribute("extranceTestHelper") @Valid ExtranceTestHelper extranceTestHelper, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("recruitId") Long recruitId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("extranceTestMessage", "Проверьте корректность введенных данных!");
            return "redirect:/user/extranceTestPage/" + recruitId;
        }
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        ExtranceTest recruitExtranceTest = selectedRecruit.getExtranceTest();

        byte hbScore = hbStandartService.getScoreByResult(extranceTestHelper.getHbResult());
        byte run100Score = run100StandartService.getScoreByResult(extranceTestHelper.getRun100mResult());
        byte run3Score = run3StandartService.getScoreByResult(extranceTestHelper.getRun3kmResult());
        byte prof_group = extranceTestHelper.getProf_group();

        if (recruitExtranceTest != null) {
            recruitExtranceTest.setExtranceTestId(selectedRecruit.getExtranceTest().getExtranceTestId());
            recruitExtranceTest.setHorizontal_bar(hbScore);
            recruitExtranceTest.setRun100m(run100Score);
            recruitExtranceTest.setRun3km(run3Score);
            recruitExtranceTest.setProf_group(prof_group);
            redirectAttributes.addFlashAttribute("extranceTestMessage", "Результаты успешно изменены!");
        } else {
            recruitExtranceTest = new ExtranceTest();
            recruitExtranceTest.setHorizontal_bar(hbScore);
            recruitExtranceTest.setRun100m(run100Score);
            recruitExtranceTest.setRun3km(run3Score);
            recruitExtranceTest.setProf_group(prof_group);

            selectedRecruit.setExtranceTest(recruitExtranceTest);
            recruitExtranceTest.setRecruit(selectedRecruit);
            redirectAttributes.addFlashAttribute("extranceTestMessage", "Результаты успешно добавлены!");
        }
        extranceTestService.addExtranceTest(recruitExtranceTest);

        return "redirect:/user/extranceTestPage/" + recruitId;
    }
}
