package com.lucent.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Organization {

    @Id
    @SequenceGenerator(
            name = "organization_sequence",
            sequenceName = "organization_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "organization_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1023)
    private String description;

    private String profilePicURL;

    @OneToOne
    private Image profilePic;

    private Boolean autoApprove = false;
    private Boolean requireCode = false;
    private Boolean requireNID = false;

    private Double balance = 0.0;
    private Long memberCount = 0L;

    private boolean published = false;

    @OneToOne
    private AppUser manager;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
