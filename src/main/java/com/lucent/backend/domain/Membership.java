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
public class Membership {
    @Id
    @SequenceGenerator(
            name = "membership_sequence",
            sequenceName = "organization_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "membership_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private AppUser donor;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private  Organization organization;

    private String nid;
    private String membershipCode;
    private Boolean approved;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
