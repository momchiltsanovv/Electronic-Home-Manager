package com.nbu.electronic_home_manager.fee.controller;

import com.nbu.electronic_home_manager.fee.dto.CreateFeeRequest;
import com.nbu.electronic_home_manager.fee.dto.FeeResponse;
import com.nbu.electronic_home_manager.fee.dto.MarkFeePaidRequest;
import com.nbu.electronic_home_manager.fee.service.FeeService;
import com.nbu.electronic_home_manager.shared.dto.AmountReport;
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
    public ResponseEntity<FeeResponse> payFee(@PathVariable UUID id,
                                              @RequestBody(required = false) MarkFeePaidRequest request) {
        // If no request body provided, create an empty request (paidDate will default to current date)
        if (request == null) {
            request = new MarkFeePaidRequest();
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

    // Reports for amounts to be paid (unpaid fees)
    @GetMapping("/reports/unpaid/company/{companyId}")
    public ResponseEntity<AmountReport> getUnpaidAmountsByCompany(@PathVariable UUID companyId) {
        AmountReport report = feeService.getUnpaidAmountsByCompany(companyId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/unpaid/building/{buildingId}")
    public ResponseEntity<AmountReport> getUnpaidAmountsByBuilding(@PathVariable UUID buildingId) {
        AmountReport report = feeService.getUnpaidAmountsByBuilding(buildingId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/unpaid/employee/{employeeId}")
    public ResponseEntity<AmountReport> getUnpaidAmountsByEmployee(@PathVariable UUID employeeId) {
        AmountReport report = feeService.getUnpaidAmountsByEmployee(employeeId);
        return ResponseEntity.ok(report);
    }

    // Reports for amounts paid
    @GetMapping("/reports/paid/company/{companyId}")
    public ResponseEntity<AmountReport> getPaidAmountsByCompany(@PathVariable UUID companyId) {
        AmountReport report = feeService.getPaidAmountsByCompany(companyId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/paid/building/{buildingId}")
    public ResponseEntity<AmountReport> getPaidAmountsByBuilding(@PathVariable UUID buildingId) {
        AmountReport report = feeService.getPaidAmountsByBuilding(buildingId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/paid/employee/{employeeId}")
    public ResponseEntity<AmountReport> getPaidAmountsByEmployee(@PathVariable UUID employeeId) {
        AmountReport report = feeService.getPaidAmountsByEmployee(employeeId);
        return ResponseEntity.ok(report);
    }
}

