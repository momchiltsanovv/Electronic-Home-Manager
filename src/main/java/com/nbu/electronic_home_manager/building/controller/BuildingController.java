package com.nbu.electronic_home_manager.building.controller;

import com.nbu.electronic_home_manager.building.dto.CreateBuildingRequest;
import com.nbu.electronic_home_manager.building.dto.EditBuildingRequest;
import com.nbu.electronic_home_manager.building.service.BuildingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping("/creation")
    public ResponseEntity<Void> createBuilding(@Valid @RequestBody CreateBuildingRequest request,
                                              BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating building");
        }

        buildingService.createBuilding(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editBuilding(@PathVariable UUID id,
                                            @Valid @RequestBody EditBuildingRequest request,
                                            BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for editing building");
        }

        buildingService.editBuilding(id, request);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable UUID id) {

        buildingService.deleteBuilding(id);
        return ResponseEntity.ok().build();

    }

}
