package com.nbu.electronic_home_manager.fee.repository;

import com.nbu.electronic_home_manager.fee.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeeRepository extends JpaRepository<Fee, UUID> {
}
