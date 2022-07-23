package com.lucent.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MembershipRequest {
    @NotNull(message = "Must enter an organization id.")
    public Long organizationId;
    public String nid = null;
    public String membershipCode = null;
}
