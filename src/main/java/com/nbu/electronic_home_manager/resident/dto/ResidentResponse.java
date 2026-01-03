package com.nbu.electronic_home_manager.resident.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String phone;
    private String email;
    private Boolean usesElevator;
    private List<Integer> apartmentNumbers; // Apartment numbers where the resident lives (for building-specific queries)
}

