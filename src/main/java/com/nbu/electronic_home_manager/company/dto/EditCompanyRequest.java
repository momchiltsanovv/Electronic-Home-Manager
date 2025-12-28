package com.nbu.electronic_home_manager.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditCompanyRequest {

    @Size(min = 3, message = "Company name must be at least 3 characters")
    private String name;

    @Size(min = 10, max = 10, message = "Company phone must be exactly 10 characters")
    private String phone;

    @Size(min = 5, message = "Company address must be at least 5 characters")
    private String address;

    @Email(message = "Email must be valid")
    private String email;

}
