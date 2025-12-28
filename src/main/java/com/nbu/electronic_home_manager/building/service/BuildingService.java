package com.nbu.electronic_home_manager.building.service;


import com.nbu.electronic_home_manager.building.dto.CreateBuildingRequest;
import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.building.repository.BuildingRepository;
import com.nbu.electronic_home_manager.exception.BuildingDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BuildingService {


    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void createBuilding(CreateBuildingRequest request) {

        Building building = Building.builder()
                                    .address(request.getAddress())
                                    .floors(request.getFloors())
                                    .totalApartments(request.getTotalApartments())
                                    .builtArea(request.getBuiltArea())
                                    .commonAreas(request.getCommonAreas())
                                    .hasElevator(request.getHasElevator())
                                    .pricePerSquareMeter(request.getPricePerSquareMeter())
                                    .elevatorFeePerPerson(request.getElevatorFeePerPerson())
                                    .petFeePerPet(request.getPetFeePerPet())
                                    .build();

        buildingRepository.save(building);

    }

    public void deleteBuilding(UUID buildingId) {

        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);

        if(optionalBuilding.isEmpty())  throw new BuildingDoesNotExistException("No such building exist");

        buildingRepository.delete(optionalBuilding.get());
    }
}
