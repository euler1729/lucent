package com.lucent.backend.api.dto;

import com.lucent.backend.domain.Organization;

public class OrganizationResponse {

    public Long id;
    public String name;
    public String description;
    public Boolean autoApprove, requireCode, requireNID;

    public OrganizationResponse(Organization organization){
        this.id = organization.getId();
        this.name = organization.getName();
        this.description = organization.getDescription();
        this.autoApprove = organization.getAutoApprove();
        this.requireCode = organization.getRequireCode();
        this.requireNID = organization.getRequireNID();
    }
}
