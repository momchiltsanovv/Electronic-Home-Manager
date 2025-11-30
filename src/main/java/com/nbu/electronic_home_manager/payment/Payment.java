package com.nbu.electronic_home_manager.payment;

import com.nbu.electronic_home_manager.fee.Fee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    private LocalDate paymentDate;

    @OneToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;
}
