package com.example.committee.controller;

import com.example.committee.service.RecruitService;
import com.example.committee.service.ReportService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Controller
public class ReportController {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> getRecruitReportPdf(final HttpServletResponse response) throws ClassNotFoundException, JRException {

        InputStream inputStream = this.getClass().getResourceAsStream("/reports/recruitsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, recruitService.getDataSource());

        final byte[] data = reportService.getReportPdf(jasperPrint);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recruitReport.pdf");
        header.setContentLength(data.length);

        return new HttpEntity<byte[]>(data, header);
    }


}