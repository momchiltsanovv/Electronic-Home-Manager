package com.nbu.electronic_home_manager.apartment.model;

import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.fee.model.Fee;
import com.nbu.electronic_home_manager.resident.model.Resident;
import com.nbu.electronic_home_manager.resident.model.Pet;
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

    // MANY-TO-ONE: Many apartments belong to one building
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    // MANY-TO-ONE: Many apartments can have one owner, one person can own multiple apartments
    // This side owns the relationship - foreign key is stored here
    // Creates "owner_id" column in apartments table
    // Business rule: Each apartment can have one owner (nullable - assigned later)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private Resident owner;

    // MANY-TO-MANY: Multiple residents per apartment, one person can live in multiple apartments
    // This side owns the relationship - creates the join table
    // Creates junction table "apartment_residents" with columns: apartment_id, person_id
    // Business case: Family members, roommates, people with multiple residences
    // Note: The owner might not live in the apartment (rental property)
    @ManyToMany
    @JoinTable(
            name = "apartment_residents",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Resident> residents;

    // ONE-TO-MANY: One apartment can have many pets
    @OneToMany(mappedBy = "apartment")
    private Set<Pet> pets;

    // ONE-TO-MANY: One apartment generates many fees (monthly fees over time)
    @OneToMany(mappedBy = "apartment")
    private Set<Fee> fees;
}
