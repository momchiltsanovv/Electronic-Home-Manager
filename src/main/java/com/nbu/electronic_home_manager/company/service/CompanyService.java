package com.nbu.electronic_home_manager.company.service;

import com.nbu.electronic_home_manager.company.dto.CompanyResponse;
import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.company.repository.CompanyRepository;
import com.nbu.electronic_home_manager.exception.CompanyDoesNotExistException;
import com.nbu.electronic_home_manager.fee.model.Fee;
import com.nbu.electronic_home_manager.fee.repository.FeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyService {


    private final CompanyRepository companyRepository;
    private final FeeRepository feeRepository;

    public CompanyService(CompanyRepository companyRepository, FeeRepository feeRepository) {
        this.companyRepository = companyRepository;
        this.feeRepository = feeRepository;
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

    public List<CompanyResponse> getAllCompanies(String sortBy) {
        List<Company> companies = companyRepository.findAll();
        
        List<CompanyResponse> responses = companies.stream()
                .map(company -> {
                    BigDecimal revenue = calculateCompanyRevenue(company.getId());
                    return CompanyResponse.builder()
                            .id(company.getId())
                            .name(company.getName())
                            .address(company.getAddress())
                            .phone(company.getPhone())
                            .email(company.getEmail())
                            .totalRevenue(revenue)
                            .createdDate(company.getCreatedDate())
                            .updatedDate(company.getUpdatedDate())
                            .build();
                })
                .collect(Collectors.toList());
        
        // Sort by revenue
        if ("revenue".equalsIgnoreCase(sortBy) || "revenue_asc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing(CompanyResponse::getTotalRevenue, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("revenue_desc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing(CompanyResponse::getTotalRevenue, Comparator.nullsLast(Comparator.reverseOrder())));
        }
        
        return responses;
    }
    
    private BigDecimal calculateCompanyRevenue(UUID companyId) {
        // Get all paid fees for apartments in buildings belonging to this company
        List<Fee> paidFees = feeRepository.findPaidFeesByCompanyId(companyId);
        
        return paidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
