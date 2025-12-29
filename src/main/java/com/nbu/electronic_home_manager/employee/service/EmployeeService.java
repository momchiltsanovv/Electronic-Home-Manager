package com.nbu.electronic_home_manager.employee.service;

import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.building.repository.BuildingRepository;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.company.repository.CompanyRepository;
import com.nbu.electronic_home_manager.employee.dto.BuildingInfoResponse;
import com.nbu.electronic_home_manager.employee.dto.EditEmployeeRequest;
import com.nbu.electronic_home_manager.employee.dto.EmployeeWithBuildingsResponse;
import com.nbu.electronic_home_manager.employee.dto.RegisterEmployeeRequest;
import com.nbu.electronic_home_manager.employee.model.Employee;
import com.nbu.electronic_home_manager.employee.repository.EmployeeRepository;
import com.nbu.electronic_home_manager.exception.CompanyDoesNotExistException;
import com.nbu.electronic_home_manager.exception.EmployeeDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final BuildingRepository buildingRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           CompanyRepository companyRepository,
                           BuildingRepository buildingRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.buildingRepository = buildingRepository;
    }

    public void createEmployee(RegisterEmployeeRequest request) {
        Optional<Company> optionalCompany = companyRepository.findById(request.getCompanyId());

        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("Company with id " + request.getCompanyId() + " does not exist");
        }

        Company company = optionalCompany.get();

        Employee employee = Employee.builder()
                                    .firstName(request.getFirstName())
                                    .lastName(request.getLastName())
                                    .phone(request.getPhone())
                                    .email(request.getEmail())
                                    .salary(request.getSalary())
                                    .company(company)
                                    .build();

        employeeRepository.save(employee);
        log.info("Employee created: {} {} for company {}", request.getFirstName(), request.getLastName(), company.getName());
    }

    public void editEmployee(UUID employeeId, EditEmployeeRequest request) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeeDoesNotExistException("No such employee exists");
        }

        Employee employee = optionalEmployee.get();

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            employee.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            employee.setLastName(request.getLastName());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            employee.setPhone(request.getPhone());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            employee.setEmail(request.getEmail());
        }

        if (request.getSalary() != null) {
            employee.setSalary(request.getSalary());
        }

        employeeRepository.save(employee);
        log.info("Employee with id {} has been updated", employeeId);
    }

    @Transactional
    public void deleteEmployee(UUID employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeeDoesNotExistException("No such employee exists");
        }

        Employee employeeToDelete = optionalEmployee.get();
        Company company = employeeToDelete.getCompany();

        // Get all buildings assigned to this employee
        List<Building> buildingsToRedistribute = buildingRepository.findByEmployeeId(employeeId);

        if (!buildingsToRedistribute.isEmpty()) {
            // Get all other employees from the same company
            List<Employee> otherEmployees = employeeRepository.findByCompanyId(company.getId())
                                                              .stream()
                                                              .filter(emp -> !emp.getId().equals(employeeId))
                                                              .toList();

            if (otherEmployees.isEmpty()) {
                throw new IllegalStateException(
                        "Cannot delete employee: No other employees available to reassign " +
                                buildingsToRedistribute.size() + " building(s)");
            }

            // Redistribute buildings to remaining employees
            for (Building building : buildingsToRedistribute) {
                // Find employee with minimum number of buildings
                Employee newEmployee = otherEmployees.stream()
                                                     .min(Comparator.comparingInt(emp -> emp.getAssignedBuildings().size()))
                                                     .orElse(otherEmployees.get(0)); // Fallback to first employee

                building.setEmployee(newEmployee);
                buildingRepository.save(building);
                log.info("Building at {} reassigned from {} {} to {} {}",
                         building.getAddress(),
                         employeeToDelete.getFirstName(), employeeToDelete.getLastName(),
                         newEmployee.getFirstName(), newEmployee.getLastName());
            }
        }

        employeeRepository.delete(employeeToDelete);
        log.info("Employee {} {} with id {} has been deleted. {} building(s) were redistributed.",
                 employeeToDelete.getFirstName(), employeeToDelete.getLastName(),
                 employeeId, buildingsToRedistribute.size());
    }

    public List<EmployeeWithBuildingsResponse> getEmployeesWithBuildingsByCompany(UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("Company with id " + companyId + " does not exist");
        }

        List<Employee> employees = employeeRepository.findByCompanyId(companyId);

        return employees.stream()
                        .map(this::mapToEmployeeWithBuildingsResponse)
                        .collect(Collectors.toList());
    }

    private EmployeeWithBuildingsResponse mapToEmployeeWithBuildingsResponse(Employee employee) {
        List<BuildingInfoResponse> buildings = employee.getAssignedBuildings().stream()
                                                       .map(this::mapToBuildingInfoResponse)
                                                       .collect(Collectors.toList());

        return EmployeeWithBuildingsResponse.builder()
                                            .id(employee.getId())
                                            .firstName(employee.getFirstName())
                                            .lastName(employee.getLastName())
                                            .phone(employee.getPhone())
                                            .email(employee.getEmail())
                                            .salary(employee.getSalary())
                                            .hiredDate(employee.getHiredDate())
                                            .assignedBuildings(buildings)
                                            .buildingCount(buildings.size())
                                            .build();
    }

    private BuildingInfoResponse mapToBuildingInfoResponse(Building building) {
        return BuildingInfoResponse.builder()
                                   .id(building.getId())
                                   .address(building.getAddress())
                                   .floors(building.getFloors())
                                   .totalApartments(building.getTotalApartments())
                                   .builtArea(building.getBuiltArea())
                                   .commonAreas(building.getCommonAreas())
                                   .hasElevator(building.getHasElevator())
                                   .pricePerSquareMeter(building.getPricePerSquareMeter())
                                   .elevatorFeePerPerson(building.getElevatorFeePerPerson())
                                   .petFeePerPet(building.getPetFeePerPet())
                                   .createdDate(building.getCreatedDate())
                                   .updatedDate(building.getUpdatedDate())
                                   .build();
    }
}

