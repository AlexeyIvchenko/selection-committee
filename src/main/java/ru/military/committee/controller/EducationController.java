package ru.military.committee.controller;

import org.apache.log4j.Logger;
import ru.military.committee.domain.personal.Certificate;
import ru.military.committee.domain.personal.Exam;
import ru.military.committee.domain.personal.Recruit;
import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.Specialty;
import ru.military.committee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.military.committee.utils.LogCreator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Класс обеспечивает работу с результатами ЕГЭ и аттестатами абитуриентов.
 */
@Controller
public class EducationController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private ExamService examService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private MilitaryEducationService educationService;

    private static final Logger log = Logger.getLogger(RecruitController.class);

    /**
     * Формирует данные модели и возвращает имя html-страницы с информацией о результате ЕГЭ абитуриента и его аттестате.
     *
     * @param exam        - объект, хранящий результаты ЕГЭ абитуриента.
     * @param certificate - объект, хранящий информацию об аттестате абитуриента.
     * @param recruitId   - уникальный идентификатор абитуриента.
     * @param model       - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с информацией об образовании абитуриента.
     */
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

    /**
     * Добавляет или редактирует информацию о результатах ЕГЭ абитуриента.
     *
     * @param exam               - объект, хранящий результаты ЕГЭ абитуриента.
     * @param bindingResult      - объект, хранящий ошибки валидации объектов, полученных из html-страницы.
     * @param redirectAttributes - объект, хранящий атрибуты, передающиеся при перенаправлении.
     * @param recruitId          - уникальный идентификатор пользователя.
     * @return - перенаправляет на html-страницу с информацией об образовании абитуриента.
     */
    @PostMapping("/user/addExam/{recruitId}")
    public String addExam(@ModelAttribute("exam") @Valid Exam exam, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("recruitId") Long recruitId, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("examMessage", "Проверьте корректность введенных данных о результатах ЕГЭ!");
            return "redirect:/user/educationPage/" + recruitId;
        }
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        if (selectedRecruit.getExam() != null) {
            exam.setExamId(selectedRecruit.getExam().getExamId());
            LogCreator.logEvent(log, principal, "info", "Изменение результатов ЕГЭ абитуриента - "
                    + selectedRecruit.getSurname() + " " + selectedRecruit.getName() + " " + selectedRecruit.getSecondName() + ")");

            redirectAttributes.addFlashAttribute("examMessage", "Данные успешно изменены!");
        } else {
            selectedRecruit.setExam(exam);
            exam.setRecruit(selectedRecruit);
            LogCreator.logEvent(log, principal, "info", "Добавление результатов ЕГЭ абитуриента - "
                    + selectedRecruit.getSurname() + " " + selectedRecruit.getName() + " " + selectedRecruit.getSecondName() + ")");
            redirectAttributes.addFlashAttribute("examMessage", "Результаты ЕГЭ успешно добавлены!");
        }
        examService.addExam(exam);
        return "redirect:/user/educationPage/" + recruitId;
    }

    /**
     * Добавляет или редактирует информацию об атестате абитуриента.
     *
     * @param certificate        - объект, хранящий информацию об аттестате абитуриента.
     * @param bindingResult      - объект, хранящий ошибки валидации объектов, полученных из html-страницы.
     * @param redirectAttributes - объект, хранящий атрибуты, передающиеся при перенаправлении.
     * @param recruitId          - уникальный идентификатор пользователя.
     * @return - перенаправляет на html-страницу с информацией об образовании абитуриента.
     */
    @PostMapping("/user/addCertificate/{recruitId}")
    public String addCertificate(@ModelAttribute("certificate") @Valid Certificate certificate, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("recruitId") Long recruitId, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("certificateMessage", "Проверьте корректность введенных данных сертификата!");
            return "redirect:/user/educationPage/" + recruitId;
        }
        Recruit selectedRecruit = recruitService.getRecruitById(recruitId);
        if (selectedRecruit.getCertificate() != null) {
            certificate.setCertificateId(selectedRecruit.getCertificate().getCertificateId());
            LogCreator.logEvent(log, principal, "info", "Изменение данных аттестата абитуриента - "
                    + selectedRecruit.getSurname() + " " + selectedRecruit.getName() + " " + selectedRecruit.getSecondName() + ")");
            redirectAttributes.addFlashAttribute("certificateMessage", "Данные успешно изменены!");
        } else {
            selectedRecruit.setCertificate(certificate);
            certificate.setRecruit(selectedRecruit);
            LogCreator.logEvent(log, principal, "info", "Добавление данных аттестата абитуриента - "
                    + selectedRecruit.getSurname() + " " + selectedRecruit.getName() + " " + selectedRecruit.getSecondName() + ")");
            redirectAttributes.addFlashAttribute("certificateMessage", "Данные аттестата успешно добавлены!");
        }
        certificateService.addCertificate(certificate);
        return "redirect:/user/educationPage/" + recruitId;
    }

    @GetMapping("/admin/facultySpecialtyPage")
    public String getFacultySpecialtyPage(Model model) {
        List<Faculty> facultiesList = facultyService.getAllFaculties();
        model.addAttribute("facultiesList", facultiesList);

        List<Specialty> specialtiesList = specialtyService.getAllSpecialties();
        model.addAttribute("specialtiesList", specialtiesList);
        return "facultySpecialtyPage";
    }

    @GetMapping("/admin/facultyForm/{facultyId}")
    public String getFacultySpecialtyForm(@ModelAttribute("faculty") Faculty faculty, @PathVariable("facultyId") short facultyId, Model model) {
        if (facultyId != 0) {
            Faculty facultyFromBase = facultyService.getFacultyById(facultyId);
            model.addAttribute("faculty", facultyFromBase);
        }
        model.addAttribute("educationList", educationService.getAllMilitaryEducations());
        model.addAttribute("facultyId", facultyId);
        return "facultyForm";
    }

    @PostMapping("/admin/addFaculty/{facultyId}")
    public String addFaculty(@ModelAttribute("faculty") @Valid Faculty faculty, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("facultyId") short facultyId) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("facultyMessage", "Проверьте корректность введенных данных о факультете!");
//            return "redirect:/admin/facultyForm/" + facultyId;
//        }
//        if (facultyId == 0) {
//            facultyService.addFaculty(faculty);
//        } else {
//
//        }
        facultyService.addFaculty(faculty);
        return "redirect:/admin/facultySpecialtyPage";
    }
}
