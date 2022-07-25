package com.lucent.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Spending {
    @Id
    @SequenceGenerator(
            name = "spending_sequence",
            sequenceName = "spending_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "spending_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    private String description;

    private double amount;
    private double collectedAmount;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
