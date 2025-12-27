package com.nbu.electronic_home_manager.building.service;


import com.nbu.electronic_home_manager.building.dto.CreateBuildingRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuildingService {
    
    public void createBuilding(@Valid CreateBuildingRequest request) {
    }

    public void deleteBuilding(UUID buildingId) {
    }
}
