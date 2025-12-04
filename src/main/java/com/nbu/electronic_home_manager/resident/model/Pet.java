package com.nbu.electronic_home_manager.resident.model;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private Boolean usesCommonAreas;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;
}
