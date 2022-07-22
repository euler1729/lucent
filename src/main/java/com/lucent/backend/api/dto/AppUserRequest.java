package com.lucent.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * AppUser format for incoming request
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class AppUserRequest {


    @NotNull(message = "You must enter name.")
    private String name;

    @NotNull(message = "You must enter a valid phone number.")
    private String phone;

    @NotNull(message = "You must enter a password.")
    private String password;
}
