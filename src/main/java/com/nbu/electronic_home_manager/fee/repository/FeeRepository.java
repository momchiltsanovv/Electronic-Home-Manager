package com.nbu.electronic_home_manager.fee.repository;

import com.nbu.electronic_home_manager.fee.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    @Query("SELECT f FROM Fee f WHERE f.isPaid = true " +
           "AND f.apartment.building.company.id = :companyId")
    List<Fee> findPaidFeesByCompanyId(@Param("companyId") UUID companyId);
    
    @Query("SELECT f FROM Fee f WHERE f.isPaid = false " +
           "AND f.apartment.building.company.id = :companyId")
    List<Fee> findUnpaidFeesByCompanyId(@Param("companyId") UUID companyId);
    
    @Query("SELECT f FROM Fee f WHERE f.isPaid = true " +
           "AND f.apartment.building.id = :buildingId")
    List<Fee> findPaidFeesByBuildingId(@Param("buildingId") UUID buildingId);
    
    @Query("SELECT f FROM Fee f WHERE f.isPaid = false " +
           "AND f.apartment.building.id = :buildingId")
    List<Fee> findUnpaidFeesByBuildingId(@Param("buildingId") UUID buildingId);
    
    @Query("SELECT f FROM Fee f WHERE f.isPaid = true " +
           "AND f.apartment.building.employee.id = :employeeId")
    List<Fee> findPaidFeesByEmployeeId(@Param("employeeId") UUID employeeId);
    
    @Query("SELECT f FROM Fee f WHERE f.isPaid = false " +
           "AND f.apartment.building.employee.id = :employeeId")
    List<Fee> findUnpaidFeesByEmployeeId(@Param("employeeId") UUID employeeId);
}
