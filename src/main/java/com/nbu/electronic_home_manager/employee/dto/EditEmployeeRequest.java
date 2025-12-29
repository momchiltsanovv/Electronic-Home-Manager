package com.nbu.electronic_home_manager.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditEmployeeRequest {

    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;

    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters")
    private String phone;

    @Email(message = "Email must be valid")
    private String email;

    @Positive(message = "Salary must be a positive number")
    private BigDecimal salary;

}

