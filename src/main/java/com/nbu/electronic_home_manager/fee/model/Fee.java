package com.nbu.electronic_home_manager.fee.model;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Month month;

    private Year year;

    private BigDecimal baseAmount;
    private BigDecimal elevatorFee;
    private BigDecimal petFee;
    private BigDecimal totalAmount;

    // Simple paid/unpaid tracking
    @Column(nullable = false)
    private Boolean isPaid = false;

    // Optional: when was it paid (for basic tracking)
    private LocalDate paidDate;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

}
