package com.nbu.electronic_home_manager.company.service;

import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.company.repository.CompanyRepository;
import com.nbu.electronic_home_manager.exception.CompanyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CompanyService {


    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public void createCompany(RegisterCompanyRequest request) {
        Company company = Company.builder()
                                 .name(request.getName())
                                 .phone(request.getPhone())
                                 .address(request.getAddress())
                                 .email(request.getEmail())
                                 .build();


        companyRepository.save(company);
    }


    public void editCompany(EditCompanyRequest request) {
        Company company;


        //        companyRepository.save(company);

    }

    public void deleteCompany(UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if (optionalCompany.isEmpty()) {
            throw new CompanyNotFoundException("No such company exists");
        }

        companyRepository.delete(optionalCompany.get());
    }


}
