package com.lucent.backend.api.dto;

import com.lucent.backend.domain.Spending;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SpendingResponse {

    private Long id;
    private OrganizationResponse organizationResponse;
    private String description;
    private double amount;
    private double collectedAmount;

    public SpendingResponse(Spending spending){
        this.id = spending.getId();
        this.organizationResponse = new OrganizationResponse(spending.getOrganization());
        this.description = spending.getDescription();
        this.amount = spending.getAmount();
        this.collectedAmount = spending.getCollectedAmount();
    }

}
