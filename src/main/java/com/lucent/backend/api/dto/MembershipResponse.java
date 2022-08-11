package com.lucent.backend.api.dto;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Membership;
import com.lucent.backend.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipResponse {
    public Long id;
    public String donor;
    public String organization;
    public String nid, membershipCode;
    public boolean approved;

    public MembershipResponse(Membership membership){
        this.id = membership.getId();
        this.donor = membership.getDonor().getName();
        this.organization = membership.getOrganization().getName();
        this.nid = membership.getNid();
        this.membershipCode = membership.getMembershipCode();
        this.approved = membership.getApproved();
    }

}
