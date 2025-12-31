package com.nbu.electronic_home_manager.fee.repository;

import com.nbu.electronic_home_manager.fee.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeeRepository extends JpaRepository<Fee, UUID> {
    
    Optional<Fee> findByApartmentIdAndMonthAndYear(UUID apartmentId, Month month, Year year);
    
    List<Fee> findByApartmentIdOrderByYearDescMonthDesc(UUID apartmentId);
    
    List<Fee> findByApartmentBuildingIdOrderByYearDescMonthDesc(UUID buildingId);
}
