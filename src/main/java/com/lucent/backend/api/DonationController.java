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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController  @RequiredArgsConstructor @Slf4j
public class DonationController {
    private final DonationService donationService;
    private final AppUserService appUserService;

    /**
     * Make a donation | Donor only
     * @param donationRequest DonationRequest object containing organizationID, amount, gateway, trxid
     * @return DonationResponse
     * @throws ResourceNotFound if organization id is invalid
     */
    @PostMapping("/donate")
    public ResponseEntity<DonationResponse> donate(@Valid @RequestBody DonationRequest donationRequest) throws ResourceNotFound {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(donationService.donate(donationRequest, appUserService.getUser((String) auth.getPrincipal())));
    }

    /**
     * Get latest donations of an organization | Permit all
     * @param organizationId organization id in parameter
     * @param page page no
     * @param size page size
     * @return List of DonationResponse
     * @throws ResourceNotFound if organization id is invalid
     */
    @GetMapping("/donation/latest/{organizationId}")
    public ResponseEntity<List<DonationResponse>> getLatestDonations(
            @PathVariable Long organizationId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) throws ResourceNotFound {
        return ResponseEntity.ok().body(donationService.getDonations(organizationId, false, page, size));
    }

    /**
     * Get top donations of an organization | Permit all
     * @param organizationId organization id in parameter
     * @param page page no
     * @param size page size
     * @return List of DonationResponse
     * @throws ResourceNotFound if organization id is invalid
     */
    @GetMapping("/donation/top/{organizationId}")
    public ResponseEntity<List<DonationResponse>> getTopDonations(
            @PathVariable Long organizationId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) throws ResourceNotFound {
        return ResponseEntity.ok().body(donationService.getDonations(organizationId, true, page, size));
    }
}
