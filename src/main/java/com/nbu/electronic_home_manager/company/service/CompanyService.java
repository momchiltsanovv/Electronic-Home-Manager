package com.nbu.electronic_home_manager.company.service;

import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.company.repository.CompanyRepository;
import com.nbu.electronic_home_manager.exception.CompanyDoesNotExistException;
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


    public void editCompany(UUID companyId, EditCompanyRequest request) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("No such company exists");
        }

        Company company = optionalCompany.get();

        if (request.getName() != null && !request.getName().isBlank()) {
            company.setName(request.getName());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            company.setPhone(request.getPhone());
        }

        if (request.getAddress() != null && !request.getAddress().isBlank()) {
            company.setAddress(request.getAddress());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            company.setEmail(request.getEmail());
        }

        companyRepository.save(company);
        log.info("Company with id {} has been updated", companyId);
    }

    public void deleteCompany(UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("No such company exists");
        }

        companyRepository.delete(optionalCompany.get());
    }


}
