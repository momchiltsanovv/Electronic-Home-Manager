package com.nbu.electronic_home_manager.apartment.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentResponse {
    private UUID id;
    private Integer number;
    private Integer floor;
    private Double area;
    private UUID ownerId;
    private String ownerName; // Owner's full name if exists
    private List<UUID> residentIds;
    private Integer residentCount;
}

