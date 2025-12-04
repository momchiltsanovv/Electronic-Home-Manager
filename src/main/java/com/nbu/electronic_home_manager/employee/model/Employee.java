package com.nbu.electronic_home_manager.employee.model;

import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.company.model.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private BigDecimal salary;

    @CreationTimestamp
    private LocalDate hiredDate;

    // MANY-TO-ONE: Many employees belong to one company
    // This side owns the relationship - foreign key is stored here
    // Creates "company_id" column in employees table
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // ONE-TO-MANY: One employee is assigned to manage many buildings
    // The "employee" field in Building entity owns this relationship
    // Foreign key "employee_id" is stored in the buildings table
    // Business rule: Each building is managed by exactly one employee
    @OneToMany(mappedBy = "employee")
    private Set<Building> assignedBuildings;
}
