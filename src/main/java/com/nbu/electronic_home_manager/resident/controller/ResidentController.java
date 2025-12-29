package com.nbu.electronic_home_manager.resident.controller;

import com.nbu.electronic_home_manager.resident.dto.EditResidentRequest;
import com.nbu.electronic_home_manager.resident.dto.RegisterResidentRequest;
import com.nbu.electronic_home_manager.resident.service.ResidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping("/creation")
    public ResponseEntity<Void> createResident(@Valid @RequestBody RegisterResidentRequest request,
                                               BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating resident");
        }

        residentService.createResident(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editResident(@PathVariable UUID id,
                                            @Valid @RequestBody EditResidentRequest request,
                                            BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for editing resident");
        }

        residentService.editResident(id, request);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable UUID id) {

        residentService.deleteResident(id);
        return ResponseEntity.ok().build();

    }

}

