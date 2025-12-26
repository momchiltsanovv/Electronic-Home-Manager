package com.nbu.electronic_home_manager.company.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCompanyRequest {

   private String name;
   private String id;


}
