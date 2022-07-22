package com.lucent.backend.api;
import com.lucent.backend.api.Exception.DuplicateEmailException;
import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppUserController {

    private final AppUserService appUserService;
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @PostMapping("/user/registration")
    public ResponseEntity<AppUserResponse> registerDonor(@RequestBody @Valid AppUserRequest user, HttpServletRequest request) throws DuplicateEmailException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/registration").toUriString());
        AppUserResponse savedUser = appUserService.saveUser(user, this.getSiteURL(request));
        return ResponseEntity.created(uri).body(savedUser);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<AppUserResponse> getProfile(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().body(appUserService.getUser((String) auth.getPrincipal()));
    }


}


