package com.nbu.electronic_home_manager.resident.repository;

import com.nbu.electronic_home_manager.resident.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, UUID> {
}
