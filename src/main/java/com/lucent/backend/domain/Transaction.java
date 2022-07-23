package com.lucent.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "donation_sequence",
            sequenceName = "donation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "donation_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private AppUser donor;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private Double amount;
    private String gateway;

    @CreationTimestamp
    private LocalDateTime created;
}
