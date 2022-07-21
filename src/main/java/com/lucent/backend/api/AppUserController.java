package com.lucent.backend.api;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import com.lucent.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("/user/registration")
    public ResponseEntity<AppUserResponse> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/registration").toUriString());
        user.setRole(appUserService.getRole("ROLE_DONOR"));
        return ResponseEntity.created(uri).body(new AppUserResponse(appUserService.saveUser(user)));
    }

}

class AppUserResponse{
    public String name, email;
    public Boolean verified;
    public LocalDateTime created, updated;
    public Role role;
    AppUserResponse(AppUser user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.verified = user.isVerified();
        this.created = user.getCreated();
        this.updated = user.getUpdated();
        this.role = user.getRole();
    }
}
