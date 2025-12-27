package com.nbu.electronic_home_manager.building.model;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.employee.model.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    // Fee structure for this building
    @Column(nullable = false)
    private BigDecimal pricePerSquareMeter;

    @Column(nullable = false)
    private BigDecimal elevatorFeePerPerson;

    @Column(nullable = false)
    private BigDecimal petFeePerPet;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate updatedDate;

    // MANY-TO-ONE: Many buildings are managed by one employee
    // This side owns the relationship - foreign key is stored here
    // Creates "employee_id" column in buildings table
    // Business rule: Each building has exactly one assigned employee
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // MANY-TO-ONE: Many buildings belong to one company
    // This side owns the relationship - foreign key is stored here
    // Creates "company_id" column in buildings table
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // ONE-TO-MANY: One building contains many apartments
    // The "building" field in Apartment entity owns this relationship
    // Foreign key "building_id" is stored in the apartments table
    @OneToMany(mappedBy = "building")
    private Set<Apartment> apartments;
}
