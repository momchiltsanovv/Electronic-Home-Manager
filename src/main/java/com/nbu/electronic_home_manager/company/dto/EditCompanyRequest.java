package com.nbu.electronic_home_manager.company.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditCompanyRequest {
    private String name;
    private String id;

}
