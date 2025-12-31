package com.nbu.electronic_home_manager.fee.model;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Month month;

    @Column(nullable = false)
    private Year year;

    @Column(nullable = false)
    private BigDecimal baseAmount;

    @Column(nullable = false)
    private BigDecimal elevatorFee;

    @Column(nullable = false)
    private BigDecimal petFee;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    // Simple paid/unpaid tracking
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPaid = false;

    // Optional: when was it paid (for basic tracking)
    private LocalDate paidDate;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

}
