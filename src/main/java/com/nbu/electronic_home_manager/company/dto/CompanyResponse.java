package com.nbu.electronic_home_manager.company.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private BigDecimal totalRevenue;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}

