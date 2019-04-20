package com.example.committee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {

    @GetMapping(value = "/user/companyCreatePage")
    public String getCompanyCreatePage() {
        return "companyCreatePage";
    }
}
