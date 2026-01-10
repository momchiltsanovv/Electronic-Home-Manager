package com.nbu.electronic_home_manager.shared.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountSummaryReport<T> {
    private BigDecimal totalAmount;
    private Long totalCount; // Number of fees
    private List<T> items; // Detailed list of fees
}
