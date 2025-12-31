package com.nbu.electronic_home_manager.fee.controller;

import com.nbu.electronic_home_manager.fee.dto.CreateFeeRequest;
import com.nbu.electronic_home_manager.fee.dto.FeeResponse;
import com.nbu.electronic_home_manager.fee.dto.MarkFeePaidRequest;
import com.nbu.electronic_home_manager.fee.service.FeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/creation")
    public ResponseEntity<FeeResponse> createFee(@Valid @RequestBody CreateFeeRequest request,
                                                BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for creating fee");
        }

        FeeResponse feeResponse = feeService.createFee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(feeResponse);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<FeeResponse> markFeePaid(@PathVariable UUID id,
                                                   @Valid @RequestBody MarkFeePaidRequest request,
                                                   BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect data for marking fee as paid");
        }

        FeeResponse feeResponse = feeService.markFeePaid(id, request);
        return ResponseEntity.ok(feeResponse);
    }

    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<FeeResponse>> getFeesByApartment(@PathVariable UUID apartmentId) {
        List<FeeResponse> fees = feeService.getFeesByApartment(apartmentId);
        return ResponseEntity.ok(fees);
    }

    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<FeeResponse>> getFeesByBuilding(@PathVariable UUID buildingId) {
        List<FeeResponse> fees = feeService.getFeesByBuilding(buildingId);
        return ResponseEntity.ok(fees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeResponse> getFeeById(@PathVariable UUID id) {
        FeeResponse feeResponse = feeService.getFeeById(id);
        return ResponseEntity.ok(feeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable UUID id) {
        feeService.deleteFee(id);
        return ResponseEntity.ok().build();
    }
}

