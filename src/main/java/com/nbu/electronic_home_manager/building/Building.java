package com.nbu.electronic_home_manager.building;

import com.nbu.electronic_home_manager.apartment.Apartment;
import com.nbu.electronic_home_manager.company.Company;
import com.nbu.electronic_home_manager.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "buildings")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String address;

    @Column(nullable = false, updatable = false)
    private Integer floors;

    @Column(nullable = false, updatable = false)
    private Integer totalApartments;

    @Column(nullable = false, updatable = false)
    private Double builtArea;

    @Column(nullable = false, updatable = false)
    private Double commonAreas;

    private Boolean hasElevator;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "building")
    private Set<Apartment> apartments;
}
