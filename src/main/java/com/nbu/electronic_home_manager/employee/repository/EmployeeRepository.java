package com.nbu.electronic_home_manager.employee.repository;

import com.nbu.electronic_home_manager.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    
    List<Employee> findByCompanyId(UUID companyId);
}
