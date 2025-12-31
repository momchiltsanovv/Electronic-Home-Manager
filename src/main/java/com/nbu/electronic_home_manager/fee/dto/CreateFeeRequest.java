package com.nbu.electronic_home_manager.fee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeeRequest {

    @NotNull
    private UUID apartmentId;

    @NotNull
    private Month month;

    @NotNull
    private Year year;
}

