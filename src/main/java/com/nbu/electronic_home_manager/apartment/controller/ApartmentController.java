package com.nbu.electronic_home_manager.apartment.controller;

import com.nbu.electronic_home_manager.apartment.dto.EditApartmentRequest;
import com.nbu.electronic_home_manager.apartment.service.ApartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editApartment(@PathVariable UUID id,
                                              @Valid @RequestBody EditApartmentRequest request,
                                              BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for editing apartment");
        }

        apartmentService.editApartment(id, request);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable UUID id) {

        apartmentService.deleteApartment(id);
        return ResponseEntity.ok().build();

    }

}

