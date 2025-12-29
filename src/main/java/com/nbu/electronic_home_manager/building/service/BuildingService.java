package com.nbu.electronic_home_manager.building.service;


import com.nbu.electronic_home_manager.building.dto.CreateBuildingRequest;
import com.nbu.electronic_home_manager.building.dto.EditBuildingRequest;
import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.building.repository.BuildingRepository;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.company.repository.CompanyRepository;
import com.nbu.electronic_home_manager.employee.model.Employee;
import com.nbu.electronic_home_manager.employee.repository.EmployeeRepository;
import com.nbu.electronic_home_manager.exception.BuildingDoesNotExistException;
import com.nbu.electronic_home_manager.exception.CompanyDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BuildingService {


    private final BuildingRepository buildingRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public BuildingService(BuildingRepository buildingRepository,
                          CompanyRepository companyRepository,
                          EmployeeRepository employeeRepository) {
        this.buildingRepository = buildingRepository;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void createBuilding(CreateBuildingRequest request) {
        // Validate company exists
        Optional<Company> optionalCompany = companyRepository.findById(request.getCompanyId());
        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("Company with id " + request.getCompanyId() + " does not exist");
        }
        Company company = optionalCompany.get();

        // Find employee with least buildings for this company
        List<Employee> employees = employeeRepository.findByCompanyId(request.getCompanyId());
        
        if (employees.isEmpty()) {
            throw new IllegalStateException("Cannot create building: Company has no employees to assign it to");
        }

        // Find employee with minimum number of buildings
        Employee assignedEmployee = employees.stream()
                .min(Comparator.comparingInt(emp -> emp.getAssignedBuildings().size()))
                .orElseThrow(() -> new IllegalStateException("No employees found for company"));

        Building building = Building.builder()
                                    .address(request.getAddress())
                                    .floors(request.getFloors())
                                    .totalApartments(request.getTotalApartments())
                                    .builtArea(request.getBuiltArea())
                                    .commonAreas(request.getCommonAreas())
                                    .hasElevator(request.getHasElevator())
                                    .pricePerSquareMeter(request.getPricePerSquareMeter())
                                    .elevatorFeePerPerson(request.getElevatorFeePerPerson())
                                    .petFeePerPet(request.getPetFeePerPet())
                                    .company(company)
                                    .employee(assignedEmployee)
                                    .build();

        buildingRepository.save(building);
        log.info("Building created at address: {} and assigned to employee: {} {}", 
                request.getAddress(), assignedEmployee.getFirstName(), assignedEmployee.getLastName());
    }

    public void editBuilding(UUID buildingId, EditBuildingRequest request) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);

        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("No such building exists");
        }

        Building building = optionalBuilding.get();

        if (request.getPricePerSquareMeter() != null) {
            building.setPricePerSquareMeter(request.getPricePerSquareMeter());
        }

        if (request.getElevatorFeePerPerson() != null) {
            building.setElevatorFeePerPerson(request.getElevatorFeePerPerson());
        }

        if (request.getPetFeePerPet() != null) {
            building.setPetFeePerPet(request.getPetFeePerPet());
        }

        buildingRepository.save(building);
        log.info("Building with id {} has been updated", buildingId);
    }

    public void deleteBuilding(UUID buildingId) {

        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);

        if(optionalBuilding.isEmpty())  throw new BuildingDoesNotExistException("No such building exist");

        buildingRepository.delete(optionalBuilding.get());
        log.info("Building with id {} has been deleted", buildingId);
    }
}
