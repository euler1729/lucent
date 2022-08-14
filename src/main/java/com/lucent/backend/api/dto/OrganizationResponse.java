package com.lucent.backend.api.dto;

import com.lucent.backend.Util.ImageUtil;
import com.lucent.backend.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    public String profilePicURL;
    public String coverPicURL;

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
        this.profilePicURL = organization.getProfilePicURL();
        this.coverPicURL = organization.getCoverPicURL();
    }
}
