package com.nbu.electronic_home_manager.building.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuildingRequest {

    @NotBlank
    @Size(min = 5, message = "Building address must be at least 5 characters")
    private String address;

    @Positive(message = "floors must be a positive integer")
    private Integer floors;

    @Positive(message = "apartments must be a positive integer")
    private Integer totalApartments;


    @Positive(message = "builtArea must be a positive integer")
    private Double builtArea;

    @Positive(message = "common areas must be a positive integer")
    private Double commonAreas;

    @NotNull
    private Boolean hasElevator;

    @Positive(message = "price must be a positive number")
    private BigDecimal pricePerSquareMeter;

    @Positive(message = "elevator fee must be positive number")
    private BigDecimal elevatorFeePerPerson;

    @Positive(message = "pet fee must be positive number")
    private BigDecimal petFeePerPet;














}
