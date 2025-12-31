package com.nbu.electronic_home_manager.fee.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkFeePaidRequest {

    private LocalDate paidDate; // Optional - if not provided, uses current date
}

