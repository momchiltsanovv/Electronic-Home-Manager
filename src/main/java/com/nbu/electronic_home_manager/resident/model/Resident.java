package com.nbu.electronic_home_manager.resident.model;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "residents")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String email;

    // Used for elevator fee calculation (residents over 7 years who use elevator)
    private Boolean usesElevator;

    // ONE-TO-MANY: One person can own multiple apartments
    // This is the inverse side of the owner relationship in Apartment
    // Foreign key "owner_id" is stored in apartments table
    // mappedBy refers to the "owner" field in Apartment entity
    @OneToMany(mappedBy = "owner")
    private Set<Apartment> ownedApartments;

    // MANY-TO-MANY: Apartments this person lives in
    // This is the inverse side of the residents relationship in Apartment
    // No foreign keys here - uses the "apartment_residents" junction table
    // mappedBy refers to the "residents" field in Apartment entity
    @ManyToMany(mappedBy = "residents")
    private Set<Apartment> residenceApartments;
}
