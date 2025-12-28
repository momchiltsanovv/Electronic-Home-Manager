package com.nbu.electronic_home_manager.building.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditBuildingRequest {

    @Positive(message = "price must be a positive number")
    private BigDecimal pricePerSquareMeter;

    @DecimalMin(value = "0.0", inclusive = true, message = "elevator fee must be zero or positive")
    private BigDecimal elevatorFeePerPerson;

    @Positive(message = "pet fee must be positive number")
    private BigDecimal petFeePerPet;

}
