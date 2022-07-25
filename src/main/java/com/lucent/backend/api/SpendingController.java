package com.lucent.backend.api;

import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.SpendingRequest;
import com.lucent.backend.api.dto.SpendingResponse;
import com.lucent.backend.service.AppUserService;
import com.lucent.backend.service.SpendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController @RequiredArgsConstructor @Slf4j
public class SpendingController {
    @Autowired private SpendingService spendingService;
    @Autowired private AppUserService appUserService;

    /**
     * Managers can request to spend money on behalf of the organization | Manager only
     * @param spendingRequest SpendingRequest containing oganizationId, amount, description, gateway, trxid
     * @return SpendingResponse
     * @throws AccessDeniedException if requesting user is not the manager of the organization
     * @throws ResourceNotFound id organization id is invalide
     */
    @PostMapping("/spending/request")
    public ResponseEntity<SpendingResponse> requestSpending(@Valid @RequestBody SpendingRequest spendingRequest) throws AccessDeniedException, ResourceNotFound {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(new SpendingResponse(spendingService.processSpending(spendingRequest, appUserService.getUser((String) auth.getPrincipal()))));
    }

    /**
     * Get latest spendings of an organization | Permit all
     * @param organizationId organization id in parameter
     * @param page page no
     * @param size page size
     * @return List of SpendingResponse
     * @throws ResourceNotFound if organization id is invalid
     */
    @GetMapping("/spending/latest/{organizationId}")
    public ResponseEntity<List<SpendingResponse>> getLatestSpending(
            @PathVariable Long organizationId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) throws ResourceNotFound {
        return ResponseEntity.ok().body(spendingService.getSpendings(organizationId, page, size));
    }
}
