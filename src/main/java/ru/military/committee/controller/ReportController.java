package ru.military.committee.controller;

import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.Specialty;
import ru.military.committee.service.RecruitService;
import ru.military.committee.service.ReportService;
import ru.military.committee.service.RequestService;
import ru.military.committee.utils.CascadingSelectHelper;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;

/**
 * Отчевает за формирование отчетов.
 */
@Controller
public class ReportController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RequestService requestService;

    //Пути до шаблонов отчетов
    private static final String PATH_TO_STATISTIC_REPORT = "/reports/StatisticReport.jrxml";
    private static final String PATH_TO_ORDER_REPORT = "/reports/orderReport.jrxml";
    private static final String PATH_TO_FACULTY1_REPORT = "/reports/faculty1Report.jrxml";
    private static final String PATH_TO_FACULTY2_REPORT = "/reports/faculty2Report.jrxml";
    private static final String PATH_TO_FACULTY4_REPORT = "/reports/faculty4Report.jrxml";
    private static final String PATH_TO_FACULTY5_REPORT = "/reports/faculty5Report.jrxml";

    /**
     * Формирует отчет, содержащий конкурсные списки абитуриентов.
     *
     * @param cascadingSelectHelper - вспомогательный объект для каскадного обновления выпадающих списков.
     * @return - сформированный отчет с конкурсными списками.
     */
    @GetMapping(value = "/user/exportToPDFCompetitionList", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> getCompetitionList(@ModelAttribute("cascadingSelectHelper") CascadingSelectHelper cascadingSelectHelper) throws JRException {

        String pathToReportFile = "";
        Faculty faculty = cascadingSelectHelper.getFaculty();
        switch (faculty.getFacultyId()) {
            case 1:
            case 3: {
                pathToReportFile = PATH_TO_FACULTY1_REPORT;
                break;
            }
            case 2: {
                pathToReportFile = PATH_TO_FACULTY2_REPORT;
                break;
            }
            case 4: {
                pathToReportFile = PATH_TO_FACULTY4_REPORT;
                break;
            }
            case 5: {
                pathToReportFile = PATH_TO_FACULTY5_REPORT;
                break;
            }
        }
        InputStream inputStream = this.getClass().getResourceAsStream(pathToReportFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        //заполняем отчет данными
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, requestService.getDataSource(cascadingSelectHelper.getSpecialty()));

        final byte[] data = reportService.getReportPdf(jasperPrint);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recruitReport.pdf");
        header.setContentLength(data.length);

        return new HttpEntity<byte[]>(data, header);
    }

    /**
     * Формирует отчет, содержащий статистику по зарегистрированным абитуриентам.
     *
     * @param registrationYear - год регистрации.
     * @return - сформированный отчет со статистикой.
     */
    @GetMapping(value = "/user/exportToPDFStatistic", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> getStatistic(@RequestParam(name = "registrationYear") short registrationYear) throws JRException {

        InputStream inputStream = this.getClass().getResourceAsStream(PATH_TO_STATISTIC_REPORT);
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        //заполняем отчет данными
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, recruitService.getDataSource(registrationYear));

        final byte[] data = reportService.getReportPdf(jasperPrint);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=statisticReport.pdf");
        header.setContentLength(data.length);

        return new HttpEntity<byte[]>(data, header);
    }

    @GetMapping(value = "/user/exportToPDFAdmissionOrder", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> getOrder(@RequestParam(name = "specialty2") Specialty specialty) throws JRException {

        InputStream inputStream = this.getClass().getResourceAsStream(PATH_TO_ORDER_REPORT);
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        //заполняем отчет данными
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, requestService.getOrderSource(specialty));

        final byte[] data = reportService.getReportPdf(jasperPrint);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=orderReport.pdf");
        header.setContentLength(data.length);

        return new HttpEntity<byte[]>(data, header);
    }
}
