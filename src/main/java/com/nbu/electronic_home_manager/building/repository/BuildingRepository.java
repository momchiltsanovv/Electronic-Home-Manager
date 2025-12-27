package com.nbu.electronic_home_manager.building.repository;

import com.nbu.electronic_home_manager.building.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {

}
