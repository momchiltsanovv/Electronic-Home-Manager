package com.nbu.electronic_home_manager.apartment;

import com.nbu.electronic_home_manager.building.Building;
import com.nbu.electronic_home_manager.fee.Fee;
import com.nbu.electronic_home_manager.resident.Person;
import com.nbu.electronic_home_manager.resident.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private Integer number;

    @Column(nullable = false, updatable = false)
    private Integer floor;

    @Column(nullable = false, updatable = false)
    private Double area;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    // Multiple owners per apartment
    @ManyToMany
    @JoinTable(
            name = "apartment_owners",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> owners;

    // Multiple residents per apartment
    @ManyToMany
    @JoinTable(
            name = "apartment_residents",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> residents;

    @OneToMany(mappedBy = "apartment")
    private Set<Pet> pets;

    @OneToMany(mappedBy = "apartment")
    private Set<Fee> fees;
}
