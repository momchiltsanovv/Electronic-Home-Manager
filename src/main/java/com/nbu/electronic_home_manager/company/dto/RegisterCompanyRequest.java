package com.nbu.electronic_home_manager.company.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCompanyRequest {

   @NotBlank
   @Size(min = 3, message = "Company name must be at least 3 characters")
   private String name;

   @NotBlank
   @Size(min = 10, max =10, message = "Company phone must  exactly 10 characters")
   private String phone;

   @NotBlank
   @Size(min = 5, message = "Company address must be at least 5 characters")
   private String address;

   @Email
   private String email;


}
