package com.nbu.electronic_home_manager.shared.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountReport {
    private UUID id;
    private String name; // Company name, building address, or employee name
    private BigDecimal totalAmount;
    private Long feeCount; // Number of fees
}

