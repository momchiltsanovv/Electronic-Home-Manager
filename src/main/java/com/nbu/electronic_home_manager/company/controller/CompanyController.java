package com.nbu.electronic_home_manager.company.controller;

import com.nbu.electronic_home_manager.company.dto.CompanyResponse;
import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import com.nbu.electronic_home_manager.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/creation")
    public ResponseEntity<Void> createCompany(@Valid @RequestBody RegisterCompanyRequest request,
                                              BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating company");
        }

        companyService.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editCompany(@PathVariable UUID id,
                                            @Valid @RequestBody EditCompanyRequest request,
                                            BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for editing company");
        }

        companyService.editCompany(id, request);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID id) {

        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false, defaultValue = "") String sortBy) {
        List<CompanyResponse> companies = companyService.getAllCompanies(sortBy);
        return ResponseEntity.ok(companies);
    }

}
