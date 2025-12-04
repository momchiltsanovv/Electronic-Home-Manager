package com.nbu.electronic_home_manager.company.model;

import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.employee.model.Employee;
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
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    private BigDecimal totalRevenue;

    // ONE-TO-MANY: One company manages many buildings
    // The "company" field in Building entity owns this relationship
    // Foreign key "company_id" is stored in the buildings table
    @OneToMany(mappedBy = "company")
    private Set<Building> buildings;

    // ONE-TO-MANY: One company employs many employees
    // The "company" field in Employee entity owns this relationship
    // Foreign key "company_id" is stored in the employees table
    @OneToMany(mappedBy = "company")
    private Set<Employee> employees;

    @CreationTimestamp
    private LocalDate createdDate;
}
