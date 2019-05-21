package com.example.committee.controller;

import com.example.committee.domain.request.Faculty;
import com.example.committee.service.RecruitService;
import com.example.committee.service.ReportService;
import com.example.committee.service.RequestService;
import com.example.committee.utils.CascadingSelectHelper;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;

@Controller
public class ReportController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RequestService requestService;

    @GetMapping(value = "/user/exportToPDFCompetitionList", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> getRecruitReportPdf(@ModelAttribute("cascadingSelectHelper") CascadingSelectHelper cascadingSelectHelper) throws JRException {

        String pathToReportFile = "";
        Faculty faculty = cascadingSelectHelper.getFaculty();
        switch (faculty.getFacultyId()) {
            case 1:
            case 3: {
                pathToReportFile = "/reports/faculty1Report.jrxml";
                break;
            }
            case 2: {
                pathToReportFile = "/reports/faculty2Report.jrxml";
                break;
            }
            case 4: {
                pathToReportFile = "/reports/faculty4Report.jrxml";
                break;
            }
            case 5: {
                pathToReportFile = "/reports/faculty5Report.jrxml";
                break;
            }
        }

        InputStream inputStream = this.getClass().getResourceAsStream(pathToReportFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, requestService.getDataSource(cascadingSelectHelper.getSpecialty()));

        final byte[] data = reportService.getReportPdf(jasperPrint);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recruitReport.pdf");
        header.setContentLength(data.length);

        return new HttpEntity<byte[]>(data, header);
    }


}
