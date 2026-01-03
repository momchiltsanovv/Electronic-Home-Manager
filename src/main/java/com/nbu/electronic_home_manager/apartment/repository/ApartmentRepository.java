package com.nbu.electronic_home_manager.apartment.repository;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {
    
    Optional<Apartment> findByIdAndBuildingId(UUID apartmentId, UUID buildingId);
    
    java.util.List<Apartment> findByBuildingId(UUID buildingId);
}
