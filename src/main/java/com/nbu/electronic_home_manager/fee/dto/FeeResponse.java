package com.nbu.electronic_home_manager.fee.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeResponse {

    private UUID id;
    private UUID apartmentId;
    private Integer apartmentNumber;
    private Month month;
    private Year year;
    private BigDecimal baseAmount;
    private BigDecimal elevatorFee;
    private BigDecimal petFee;
    private BigDecimal totalAmount;
    private Boolean isPaid;
    private LocalDate paidDate;
}

