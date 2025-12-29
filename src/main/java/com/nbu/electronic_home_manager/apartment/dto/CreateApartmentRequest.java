package com.nbu.electronic_home_manager.apartment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApartmentRequest {

    @NotNull
    @Positive(message = "Apartment number must be a positive integer")
    private Integer number;

    @NotNull
    @Positive(message = "Floor must be a positive integer")
    private Integer floor;

    @NotNull
    @Positive(message = "Area must be a positive number")
    private Double area;

    @NotNull
    private UUID buildingId;

    @NotNull
    private UUID ownerId;

    private List<UUID> residentIds;

}

