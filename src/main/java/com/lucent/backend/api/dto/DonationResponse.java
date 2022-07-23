package com.lucent.backend.api.dto;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Donation;
import com.lucent.backend.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DonationResponse {
    public Long id;
    public AppUser donor;
    public Organization organization;
    public Double amount;

    public DonationResponse(Donation donation){
        this.id = donation.getId();
        this.donor = donation.getDonor();
        this.organization = donation.getOrganization();
        this.amount = donation.getAmount();
    }

}
