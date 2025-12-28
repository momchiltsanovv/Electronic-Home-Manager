package com.nbu.electronic_home_manager.resident.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResidentRequest {

    @NotBlank
    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;

    @NotBlank
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;

    @Positive
    private Integer age;

    @NotBlank
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters")
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Boolean usesElevator;



}
