package com.lucent.backend.api.dto;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @AllArgsConstructor @NoArgsConstructor
public class DonationRequest {
    @NotNull(message = "Must specify an organization.")
    public Long organizationID;
    @NotNull(message = "Must specify an amount.")
    public Double amount;
    @NotNull(message = "Must specify a gateway.")
    public String gateway;
}
