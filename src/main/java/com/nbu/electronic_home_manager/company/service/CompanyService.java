package com.nbu.electronic_home_manager.company.service;

import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.company.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompanyService {


    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public void createCompany(RegisterCompanyRequest request) {
        Company company;


//        companyRepository.save(company);
    }


    public void editCompany(EditCompanyRequest request) {
        Company company;


//        companyRepository.save(company);

    }

    public void deleteCompany() {
        Company company;


//        companyRepository.save();
    }


}
