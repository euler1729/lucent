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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController @RequiredArgsConstructor @Slf4j
public class SpendingController {
    @Autowired private SpendingService spendingService;
    @Autowired private AppUserService appUserService;

    @PostMapping("/spending/request")
    public ResponseEntity<SpendingResponse> requestSpending(@Valid @RequestBody SpendingRequest spendingRequest) throws AccessDeniedException, ResourceNotFound {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return ResponseEntity.ok().body(auth.getName());
        return ResponseEntity.ok().body(new SpendingResponse(spendingService.processSpending(spendingRequest, appUserService.getUser((String) auth.getPrincipal()))));
    }
}
