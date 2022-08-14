package com.lucent.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrganizationRequest {
    @NotNull(message = "You must enter name.")
    private String name;

    @NotNull(message = "You must enter a valid phone number.")
    private String description;

    @NotNull(message = "You must select a profile picture.")
    private byte[] profilePic;

    private String profilePicName;
    private String profilePicType;
}
