package com.nbu.electronic_home_manager.building.controller;

import com.nbu.electronic_home_manager.building.dto.CreateBuildingRequest;
import com.nbu.electronic_home_manager.building.dto.EditBuildingRequest;
import com.nbu.electronic_home_manager.building.service.BuildingService;
import com.nbu.electronic_home_manager.company.dto.EditCompanyRequest;
import com.nbu.electronic_home_manager.company.dto.RegisterCompanyRequest;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/building")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping("/creation")
    public void makeBuilding(@Valid CreateBuildingRequest request,
                            BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating building");
        }

        buildingService.createBuilding(request);


    }

    @PostMapping("/details")
    public void editBuilding(EditBuildingRequest request) {

        //Todo implement edit building

    }

    @PostMapping("/deletion")
    public void deleteBuilding(@RequestParam UUID buildingId) {

        buildingService.deleteBuilding(buildingId);

    }






}
