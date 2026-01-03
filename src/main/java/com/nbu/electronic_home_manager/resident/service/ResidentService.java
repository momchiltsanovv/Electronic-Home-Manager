package com.nbu.electronic_home_manager.resident.service;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import com.nbu.electronic_home_manager.apartment.repository.ApartmentRepository;
import com.nbu.electronic_home_manager.exception.ApartmentDoesNotExistException;
import com.nbu.electronic_home_manager.exception.ResidentDoesNotExistException;
import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.building.repository.BuildingRepository;
import com.nbu.electronic_home_manager.exception.BuildingDoesNotExistException;
import com.nbu.electronic_home_manager.resident.dto.EditResidentRequest;
import com.nbu.electronic_home_manager.resident.dto.RegisterResidentRequest;
import com.nbu.electronic_home_manager.resident.dto.ResidentResponse;
import com.nbu.electronic_home_manager.resident.model.Resident;
import com.nbu.electronic_home_manager.resident.repository.ResidentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final ApartmentRepository apartmentRepository;
    private final BuildingRepository buildingRepository;

    public ResidentService(ResidentRepository residentRepository,
                          ApartmentRepository apartmentRepository,
                          BuildingRepository buildingRepository) {
        this.residentRepository = residentRepository;
        this.apartmentRepository = apartmentRepository;
        this.buildingRepository = buildingRepository;
    }

    @Transactional
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

        // Assign resident to apartment (required)
        assignResidentToApartment(resident, request.getBuildingId(), request.getApartmentId());
    }

    private void assignResidentToApartment(Resident resident, UUID buildingId, UUID apartmentId) {
        Optional<Apartment> optionalApartment = apartmentRepository.findByIdAndBuildingId(apartmentId, buildingId);

        if (optionalApartment.isEmpty()) {
            throw new ApartmentDoesNotExistException(
                    "Apartment with id " + apartmentId + " does not exist in building " + buildingId);
        }

        Apartment apartment = optionalApartment.get();

        // Business logic: If apartment has no owner, this resident becomes the owner (bought the apartment)
        // If apartment already has an owner, resident is just a tenant (renting)
        if (apartment.getOwner() == null) {
            apartment.setOwner(resident);
            log.info("Apartment {} has no owner. Resident {} {} automatically becomes the owner (bought the apartment)", 
                    apartment.getNumber(), resident.getFirstName(), resident.getLastName());
        } else {
            log.info("Apartment {} already has owner. Resident {} {} added as tenant (renting)", 
                    apartment.getNumber(), resident.getFirstName(), resident.getLastName());
        }

        // Initialize residents set if null
        if (apartment.getResidents() == null) {
            apartment.setResidents(new HashSet<>());
        }

        // Add resident to apartment
        apartment.getResidents().add(resident);
        apartmentRepository.save(apartment);

        log.info("Resident {} {} assigned to apartment {} in building {}", 
                resident.getFirstName(), resident.getLastName(), 
                apartment.getNumber(), apartment.getBuilding().getAddress());
    }

    @Transactional
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

        // Handle apartment reassignment if both buildingId and apartmentId are provided
        if (request.getBuildingId() != null && request.getApartmentId() != null) {
            reassignResidentToApartment(resident, request.getBuildingId(), request.getApartmentId());
        } else if (request.getBuildingId() != null || request.getApartmentId() != null) {
            throw new IllegalArgumentException("Both buildingId and apartmentId must be provided together for apartment reassignment");
        }

        residentRepository.save(resident);
        log.info("Resident with id {} has been updated", residentId);
    }

    private void reassignResidentToApartment(Resident resident, UUID buildingId, UUID apartmentId) {
        // Remove resident from all current apartments
        if (resident.getResidenceApartments() != null && !resident.getResidenceApartments().isEmpty()) {
            for (Apartment currentApartment : resident.getResidenceApartments()) {
                if (currentApartment.getResidents() != null) {
                    currentApartment.getResidents().remove(resident);
                    apartmentRepository.save(currentApartment);
                }
            }
            log.info("Resident {} {} removed from previous apartments", 
                    resident.getFirstName(), resident.getLastName());
        }

        // Assign resident to new apartment
        assignResidentToApartment(resident, buildingId, apartmentId);
    }

    public void deleteResident(UUID residentId) {
        Optional<Resident> optionalResident = residentRepository.findById(residentId);

        if (optionalResident.isEmpty()) {
            throw new ResidentDoesNotExistException("No such resident exists");
        }

        residentRepository.delete(optionalResident.get());
        log.info("Resident with id {} has been deleted", residentId);
    }

    public List<ResidentResponse> getAllResidents(String sortBy) {
        List<Resident> residents = residentRepository.findAll();
        
        List<ResidentResponse> responses = residents.stream()
                .map(this::mapToResidentResponse)
                .collect(Collectors.toList());
        
        // Sort by name or age
        if ("name".equalsIgnoreCase(sortBy) || "name_asc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing((ResidentResponse r) -> 
                    (r.getFirstName() + " " + r.getLastName()).toLowerCase()));
        } else if ("name_desc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing((ResidentResponse r) -> 
                    (r.getFirstName() + " " + r.getLastName()).toLowerCase(), Comparator.reverseOrder()));
        } else if ("age".equalsIgnoreCase(sortBy) || "age_asc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing(ResidentResponse::getAge, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("age_desc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing(ResidentResponse::getAge, Comparator.nullsLast(Comparator.reverseOrder())));
        }
        
        return responses;
    }

    public List<ResidentResponse> getResidentsByBuilding(UUID buildingId, String sortBy) {
        // Validate building exists
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("Building with id " + buildingId + " does not exist");
        }

        Building building = optionalBuilding.get();
        
        // Map to track residents and their apartment numbers in this building
        Map<Resident, List<Integer>> residentApartmentMap = new HashMap<>();
        
        if (building.getApartments() != null) {
            for (Apartment apartment : building.getApartments()) {
                if (apartment.getResidents() != null) {
                    for (Resident resident : apartment.getResidents()) {
                        residentApartmentMap.computeIfAbsent(resident, k -> new ArrayList<>())
                                            .add(apartment.getNumber());
                    }
                }
            }
        }
        
        List<ResidentResponse> responses = residentApartmentMap.entrySet().stream()
                .map(entry -> mapToResidentResponseWithApartments(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        
        // Sort by name or age
        if ("name".equalsIgnoreCase(sortBy) || "name_asc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing((ResidentResponse r) -> 
                    (r.getFirstName() + " " + r.getLastName()).toLowerCase()));
        } else if ("name_desc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing((ResidentResponse r) -> 
                    (r.getFirstName() + " " + r.getLastName()).toLowerCase(), Comparator.reverseOrder()));
        } else if ("age".equalsIgnoreCase(sortBy) || "age_asc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing(ResidentResponse::getAge, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("age_desc".equalsIgnoreCase(sortBy)) {
            responses.sort(Comparator.comparing(ResidentResponse::getAge, Comparator.nullsLast(Comparator.reverseOrder())));
        }
        
        return responses;
    }

    private ResidentResponse mapToResidentResponse(Resident resident) {
        return ResidentResponse.builder()
                .id(resident.getId())
                .firstName(resident.getFirstName())
                .lastName(resident.getLastName())
                .age(resident.getAge())
                .phone(resident.getPhone())
                .email(resident.getEmail())
                .usesElevator(resident.getUsesElevator())
                .apartmentNumbers(null) // Not applicable for general resident list
                .build();
    }

    private ResidentResponse mapToResidentResponseWithApartments(Resident resident, List<Integer> apartmentNumbers) {
        return ResidentResponse.builder()
                .id(resident.getId())
                .firstName(resident.getFirstName())
                .lastName(resident.getLastName())
                .age(resident.getAge())
                .phone(resident.getPhone())
                .email(resident.getEmail())
                .usesElevator(resident.getUsesElevator())
                .apartmentNumbers(apartmentNumbers)
                .build();
    }
}

