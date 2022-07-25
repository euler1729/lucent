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
public class DonationCollection {
    @Id
    @SequenceGenerator(
            name = "donationCollection_sequence",
            sequenceName = "donationCollection_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "donationCollection_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "spending_id")
    private Spending spending;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    private Boolean collected = false;
    private Boolean notified = false;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
