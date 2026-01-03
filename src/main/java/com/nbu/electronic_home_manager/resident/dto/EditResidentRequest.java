package com.nbu.electronic_home_manager.resident.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditResidentRequest {

    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;

    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;

    @Positive(message = "Age must be a positive integer")
    private Integer age;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters")
    private String phone;

    @Email(message = "Email must be valid")
    private String email;

    private Boolean usesElevator;

    // Optional: Reassign resident to a different apartment
    // If provided, both buildingId and apartmentId must be provided
    private UUID buildingId;
    private UUID apartmentId;

}

