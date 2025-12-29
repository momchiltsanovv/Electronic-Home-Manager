package com.nbu.electronic_home_manager.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithBuildingsResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private BigDecimal salary;
    private LocalDate hiredDate;
    private List<BuildingInfoResponse> assignedBuildings;
    private Integer buildingCount;

}

