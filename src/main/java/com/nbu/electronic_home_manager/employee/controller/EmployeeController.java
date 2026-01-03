package com.nbu.electronic_home_manager.employee.controller;

import com.nbu.electronic_home_manager.employee.dto.EmployeeResponse;
import com.nbu.electronic_home_manager.employee.dto.EmployeeWithBuildingsResponse;
import com.nbu.electronic_home_manager.employee.dto.EditEmployeeRequest;
import com.nbu.electronic_home_manager.employee.dto.RegisterEmployeeRequest;
import com.nbu.electronic_home_manager.shared.dto.SummaryReport;
import com.nbu.electronic_home_manager.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/creation")
    public ResponseEntity<Void> createEmployee(@Valid @RequestBody RegisterEmployeeRequest request,
                                               BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating employee");
        }

        employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editEmployee(@PathVariable UUID id,
                                             @Valid @RequestBody EditEmployeeRequest request,
                                             BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for editing employee");
        }

        employeeService.editEmployee(id, request);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {

        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/by-company/{companyId}/buildings")
    public ResponseEntity<SummaryReport<EmployeeWithBuildingsResponse>> getEmployeesWithBuildingsByCompany(
            @PathVariable UUID companyId) {

        List<EmployeeWithBuildingsResponse> employees = employeeService.getEmployeesWithBuildingsByCompany(companyId);
        SummaryReport<EmployeeWithBuildingsResponse> report = SummaryReport.<EmployeeWithBuildingsResponse>builder()
                .totalCount((long) employees.size())
                .items(employees)
                .build();
        return ResponseEntity.ok(report);

    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(
            @RequestParam(required = false, defaultValue = "") String sortBy) {
        List<EmployeeResponse> employees = employeeService.getAllEmployees(sortBy);
        return ResponseEntity.ok(employees);
    }

}

