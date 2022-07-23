package com.lucent.backend.api.dto;

import com.lucent.backend.domain.Organization;

public class OrganizationResponse {

    public Long id;
    public String name;
    public String description;

    public OrganizationResponse(Organization organization){
        this.id = organization.getId();
        this.name = organization.getName();
        this.description = organization.getDescription();
    }
}
