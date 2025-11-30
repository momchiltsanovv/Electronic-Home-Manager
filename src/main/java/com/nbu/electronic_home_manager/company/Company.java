package com.nbu.electronic_home_manager.company;

import com.nbu.electronic_home_manager.building.Building;
import com.nbu.electronic_home_manager.employee.Employee;
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

    @OneToMany(mappedBy = "company")
    private Set<Building> buildings;

    @OneToMany(mappedBy = "company")
    private Set<Employee> employees;

    @CreationTimestamp
    private LocalDate createdDate;
}
