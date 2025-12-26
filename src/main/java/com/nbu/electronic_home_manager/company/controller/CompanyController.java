package com.nbu.electronic_home_manager.company.controller;

import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/company")
public class CompanyController {

    @PostMapping("/creation")
    public void makeCompany(@Valid RegisterCompanyRequest request,
                            BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating company");
        }

        System.out.println("uspeshno registrirana companiq");

    }

    @PostMapping("/details")
    public void editCompany(EditCompanyRequest request) {


    }

    @PostMapping("/deletion")
    public void deleteCompany() {

    }



}
