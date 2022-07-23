package com.lucent.backend.api;

import com.lucent.backend.Repo.DonationRepo;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.api.dto.DonationRequest;
import com.lucent.backend.api.dto.DonationResponse;
import com.lucent.backend.service.AppUserService;
import com.lucent.backend.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController  @RequiredArgsConstructor @Slf4j
public class DonationController {
    private final DonationService donationService;
    private final AppUserService appUserService;

    @PostMapping("/donate")
    public ResponseEntity<DonationResponse> donate(@RequestBody DonationRequest donationRequest) throws ResourceNotFound {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(donationService.donate(donationRequest, appUserService.getUser((String) auth.getPrincipal())));
    }
}
