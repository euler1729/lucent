package com.lucent.backend.api.dto;

import com.lucent.backend.domain.Organization;

public class OrganizationResponse {

    public String name;
    public String description;

    public OrganizationResponse(Organization organization){
        this.name = organization.getName();
        this.description = organization.getDescription();
    }
}
