package com.lucent.backend.api.dto;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;

import java.time.LocalDateTime;

public class AppUserResponse{
    public String name, email;
    public Boolean verified;
    public LocalDateTime created, updated;
    public Role role;
    public AppUserResponse(AppUser user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.verified = user.isVerified();
        this.created = user.getCreated();
        this.updated = user.getUpdated();
        this.role = user.getRole();
    }
}
