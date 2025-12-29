package com.nbu.electronic_home_manager.resident.service;

import com.nbu.electronic_home_manager.exception.ResidentDoesNotExistException;
import com.nbu.electronic_home_manager.resident.dto.EditResidentRequest;
import com.nbu.electronic_home_manager.resident.dto.RegisterResidentRequest;
import com.nbu.electronic_home_manager.resident.model.Resident;
import com.nbu.electronic_home_manager.resident.repository.ResidentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ResidentService {

    private final ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    public void createResident(RegisterResidentRequest request) {
        Resident resident = Resident.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .phone(request.getPhone())
                .email(request.getEmail())
                .usesElevator(request.getUsesElevator())
                .build();

        residentRepository.save(resident);
        log.info("Resident created: {} {}", request.getFirstName(), request.getLastName());
    }

    public void editResident(UUID residentId, EditResidentRequest request) {
        Optional<Resident> optionalResident = residentRepository.findById(residentId);

        if (optionalResident.isEmpty()) {
            throw new ResidentDoesNotExistException("No such resident exists");
        }

        Resident resident = optionalResident.get();

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            resident.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            resident.setLastName(request.getLastName());
        }

        if (request.getAge() != null) {
            resident.setAge(request.getAge());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            resident.setPhone(request.getPhone());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            resident.setEmail(request.getEmail());
        }

        if (request.getUsesElevator() != null) {
            resident.setUsesElevator(request.getUsesElevator());
        }

        residentRepository.save(resident);
        log.info("Resident with id {} has been updated", residentId);
    }

    public void deleteResident(UUID residentId) {
        Optional<Resident> optionalResident = residentRepository.findById(residentId);

        if (optionalResident.isEmpty()) {
            throw new ResidentDoesNotExistException("No such resident exists");
        }

        residentRepository.delete(optionalResident.get());
        log.info("Resident with id {} has been deleted", residentId);
    }
}

