package com.lucent.backend.api.dto;

import com.lucent.backend.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class OrganizationResponse {

    public Long id;
    public String name;
    public String description;
    public Boolean autoApprove, requireCode, requireNID;
    public Double balance;
    public AppUserShort manager;

    public Boolean published;
    public OrganizationResponse(Organization organization){
        this.id = organization.getId();
        this.name = organization.getName();
        this.description = organization.getDescription();
        this.balance = organization.getBalance();
        this.autoApprove = organization.getAutoApprove();
        this.requireCode = organization.getRequireCode();
        this.requireNID = organization.getRequireNID();
        this.manager = new AppUserShort(organization.getManager().getId(), organization.getManager().getName());
        this.published = organization.isPublished();
    }
}
