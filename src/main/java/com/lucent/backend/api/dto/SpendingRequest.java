package com.lucent.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @AllArgsConstructor @NoArgsConstructor
public class SpendingRequest {
    @NotNull(message = "Organization must be specified.")
    private Long oganizationId;
    @NotNull(message = "Amount must be specified.")
    private Double amount;
    @NotNull(message = "Description must be specified.")
    private String description;
}
