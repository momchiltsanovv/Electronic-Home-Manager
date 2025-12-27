package com.nbu.electronic_home_manager.company.controller;

import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import com.nbu.electronic_home_manager.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/creation")
    public void makeCompany(@Valid RegisterCompanyRequest request,
                            BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating company");
        }

        companyService.createCompany(request);

    }

    @PostMapping("/details")
    public void editCompany(EditCompanyRequest request) {

        //Todo implement edit company

    }

    @PostMapping("/deletion")
    public void deleteCompany(@RequestParam UUID companyId) {

        companyService.deleteCompany(companyId);

    }



}
