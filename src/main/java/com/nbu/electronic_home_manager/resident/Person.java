package com.nbu.electronic_home_manager.resident;

import com.nbu.electronic_home_manager.apartment.Apartment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "residents")
public class Person {

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

    private Boolean usesElevator;

    // Apartments this person owns
    @ManyToMany(mappedBy = "owners")
    private Set<Apartment> ownedApartments;

    // Apartments this person lives in
    @ManyToMany(mappedBy = "residents")
    private Set<Apartment> residenceApartments;
}
