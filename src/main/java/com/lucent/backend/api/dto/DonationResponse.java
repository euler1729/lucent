package com.lucent.backend.api.dto;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Donation;
import com.lucent.backend.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.DoubleUnaryOperator;

@Data @AllArgsConstructor @NoArgsConstructor
class AppUserShort{
    public Long id;
    public String name;
}

@Data @AllArgsConstructor @NoArgsConstructor
class OrganizationShort{
    public Long id;
    public String name;
}

@Data @AllArgsConstructor @NoArgsConstructor
public class DonationResponse {
    public Long id;
    public AppUserShort donor;
    public OrganizationShort organization;
    public Double amount;
    private LocalDateTime created;

    public DonationResponse(Donation donation){
        this.id = donation.getId();
        this.donor = new AppUserShort(donation.getDonor().getId(), donation.getDonor().getName());
        this.organization = new OrganizationShort(donation.getOrganization().getId(), donation.getOrganization().getName());
        this.amount = donation.getAmount();
        this.created = donation.getCreated();
    }

}
