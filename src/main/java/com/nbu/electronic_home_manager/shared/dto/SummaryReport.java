package com.nbu.electronic_home_manager.shared.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryReport<T> {
    private Long totalCount;
    private List<T> items;
}

