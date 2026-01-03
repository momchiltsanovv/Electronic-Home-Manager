package com.nbu.electronic_home_manager.building.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponse {
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
    private Long ageInDays;
}

