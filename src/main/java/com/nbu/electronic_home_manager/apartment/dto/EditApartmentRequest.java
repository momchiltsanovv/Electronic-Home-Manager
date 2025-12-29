package com.nbu.electronic_home_manager.apartment.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditApartmentRequest {

    private UUID ownerId;
    private List<UUID> residentIds;

}

