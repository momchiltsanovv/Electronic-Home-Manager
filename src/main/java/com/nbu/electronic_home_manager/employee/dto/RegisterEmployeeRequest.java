package com.nbu.electronic_home_manager.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeRequest {

    @NotBlank
    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;

    @NotBlank
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;

    @NotBlank
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters")
    private String phone;

    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

    @NotNull
    @Positive(message = "Salary must be a positive number")
    private BigDecimal salary;

    @NotNull
    private UUID companyId;

}

