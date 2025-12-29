package com.nbu.electronic_home_manager.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingInfoResponse {

    private UUID id;
    private String address;
    private Integer floors;
    private Integer totalApartments;
    private Double builtArea;
    private Double commonAreas;
    private Boolean hasElevator;
    private BigDecimal pricePerSquareMeter;
    private BigDecimal elevatorFeePerPerson;
    private BigDecimal petFeePerPet;
    private LocalDate createdDate;
    private LocalDate updatedDate;

}

