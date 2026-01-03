package com.nbu.electronic_home_manager.employee.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private BigDecimal salary;
    private LocalDate hiredDate;
    private Integer servicedPropertiesCount;
}

