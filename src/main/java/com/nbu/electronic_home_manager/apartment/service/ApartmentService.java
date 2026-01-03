package com.nbu.electronic_home_manager.apartment.service;

import com.nbu.electronic_home_manager.apartment.dto.ApartmentResponse;
import com.nbu.electronic_home_manager.apartment.dto.CreateApartmentRequest;
import com.nbu.electronic_home_manager.apartment.dto.EditApartmentRequest;
import com.nbu.electronic_home_manager.apartment.model.Apartment;
import com.nbu.electronic_home_manager.apartment.repository.ApartmentRepository;
import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.building.repository.BuildingRepository;
import com.nbu.electronic_home_manager.exception.ApartmentDoesNotExistException;
import com.nbu.electronic_home_manager.exception.BuildingDoesNotExistException;
import com.nbu.electronic_home_manager.exception.ResidentDoesNotExistException;
import com.nbu.electronic_home_manager.resident.model.Resident;
import com.nbu.electronic_home_manager.resident.repository.ResidentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final BuildingRepository buildingRepository;
    private final ResidentRepository residentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository,
                           BuildingRepository buildingRepository,
                           ResidentRepository residentRepository) {
        this.apartmentRepository = apartmentRepository;
        this.buildingRepository = buildingRepository;
        this.residentRepository = residentRepository;
    }

    @Transactional
    public void createApartment(CreateApartmentRequest request) {
        // Validate building exists
        Optional<Building> optionalBuilding = buildingRepository.findById(request.getBuildingId());
        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("Building with id " + request.getBuildingId() + " does not exist");
        }
        Building building = optionalBuilding.get();

        // Validate owner exists
        Optional<Resident> optionalOwner = residentRepository.findById(request.getOwnerId());
        if (optionalOwner.isEmpty()) {
            throw new ResidentDoesNotExistException("Owner with id " + request.getOwnerId() + " does not exist");
        }
        Resident owner = optionalOwner.get();

        // Get residents if provided
        Set<Resident> residents = new HashSet<>();
        if (request.getResidentIds() != null && !request.getResidentIds().isEmpty()) {
            residents = request.getResidentIds().stream()
                    .map(residentId -> {
                        Optional<Resident> optionalResident = residentRepository.findById(residentId);
                        if (optionalResident.isEmpty()) {
                            throw new ResidentDoesNotExistException("Resident with id " + residentId + " does not exist");
                        }
                        return optionalResident.get();
                    })
                    .collect(Collectors.toSet());
        }

        Apartment apartment = Apartment.builder()
                .number(request.getNumber())
                .floor(request.getFloor())
                .area(request.getArea())
                .building(building)
                .owner(owner)
                .residents(residents)
                .build();

        apartmentRepository.save(apartment);
        log.info("Apartment {} created in building at {}", request.getNumber(), building.getAddress());
    }

    @Transactional
    public void editApartment(UUID apartmentId, EditApartmentRequest request) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);

        if (optionalApartment.isEmpty()) {
            throw new ApartmentDoesNotExistException("No such apartment exists");
        }

        Apartment apartment = optionalApartment.get();

        // Update owner if explicitly provided
        if (request.getOwnerId() != null) {
            Optional<Resident> optionalOwner = residentRepository.findById(request.getOwnerId());
            if (optionalOwner.isEmpty()) {
                throw new ResidentDoesNotExistException("Owner with id " + request.getOwnerId() + " does not exist");
            }
            apartment.setOwner(optionalOwner.get());
        }

        // Update residents if provided
        if (request.getResidentIds() != null && !request.getResidentIds().isEmpty()) {
            Set<Resident> residents = request.getResidentIds().stream()
                    .map(residentId -> {
                        Optional<Resident> optionalResident = residentRepository.findById(residentId);
                        if (optionalResident.isEmpty()) {
                            throw new ResidentDoesNotExistException("Resident with id " + residentId + " does not exist");
                        }
                        return optionalResident.get();
                    })
                    .collect(Collectors.toSet());
            
            // Business logic: If apartment has no owner, first resident becomes the owner (bought the apartment)
            if (apartment.getOwner() == null && !residents.isEmpty()) {
                Resident firstResident = residents.iterator().next();
                apartment.setOwner(firstResident);
                log.info("Apartment {} has no owner. Resident {} {} automatically becomes the owner (bought the apartment)", 
                        apartment.getNumber(), firstResident.getFirstName(), firstResident.getLastName());
            }
            // If apartment already has an owner, residents are just tenants (renting)
            else if (apartment.getOwner() != null) {
                log.info("Apartment {} already has owner. Residents added as tenants (renting)", apartment.getNumber());
            }
            
            apartment.setResidents(residents);
        }

        apartmentRepository.save(apartment);
        log.info("Apartment with id {} has been updated", apartmentId);
    }

    public void deleteApartment(UUID apartmentId) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);

        if (optionalApartment.isEmpty()) {
            throw new ApartmentDoesNotExistException("No such apartment exists");
        }

        apartmentRepository.delete(optionalApartment.get());
        log.info("Apartment with id {} has been deleted", apartmentId);
    }

    public List<ApartmentResponse> getApartmentsByBuilding(UUID buildingId) {
        // Validate building exists
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("Building with id " + buildingId + " does not exist");
        }

        List<Apartment> apartments = apartmentRepository.findByBuildingId(buildingId);
        
        return apartments.stream()
                .map(this::mapToApartmentResponse)
                .sorted(Comparator.comparing(ApartmentResponse::getNumber))
                .collect(Collectors.toList());
    }

    private ApartmentResponse mapToApartmentResponse(Apartment apartment) {
        String ownerName = null;
        if (apartment.getOwner() != null) {
            ownerName = apartment.getOwner().getFirstName() + " " + apartment.getOwner().getLastName();
        }
        
        List<UUID> residentIds = apartment.getResidents() != null
                ? apartment.getResidents().stream()
                        .map(Resident::getId)
                        .collect(Collectors.toList())
                : Collections.emptyList();
        
        int residentCount = apartment.getResidents() != null ? apartment.getResidents().size() : 0;
        
        return ApartmentResponse.builder()
                .id(apartment.getId())
                .number(apartment.getNumber())
                .floor(apartment.getFloor())
                .area(apartment.getArea())
                .ownerId(apartment.getOwner() != null ? apartment.getOwner().getId() : null)
                .ownerName(ownerName)
                .residentIds(residentIds)
                .residentCount(residentCount)
                .build();
    }
}

