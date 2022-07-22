package com.lucent.backend.api.dto;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;

import java.time.LocalDateTime;

/**
 * AppUser format for outgoing response
 */
public class AppUserResponse{
    public String name, phone;
    public Boolean verified;
    public LocalDateTime created, updated;
    public Role role;
    public AppUserResponse(AppUser user){
        this.name = user.getName();
        this.phone = user.getPhone();
        this.verified = user.isVerified();
        this.created = user.getCreated();
        this.updated = user.getUpdated();
        this.role = user.getRole();
    }
}
