package com.lucent.backend.api;

import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.api.dto.MembershipRequest;
import com.lucent.backend.api.dto.MembershipResponse;
import com.lucent.backend.service.AppUserService;
import com.lucent.backend.service.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @RequiredArgsConstructor @Slf4j
public class MembershipController {

    @Autowired private MembershipService membershipService;
    @Autowired private AppUserService appUserService;

    /**
     * Donors request for membership
     * @param membershipRequest Contains organizationId and nid, code if applicable
     * @return MembershipResponse
     * @throws ResourceNotFound if organizationId is invalid
     */
    @Operation(summary = "Membership Request")
    @ApiResponse(responseCode = "200", description = "Returns requested membership information.")
    @PostMapping("/membership/request")
    public ResponseEntity<MembershipResponse> requestMembership(@RequestBody MembershipRequest membershipRequest) throws ResourceNotFound {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/membership/request").toUriString());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.created(uri).body(membershipService.saveMembership(membershipRequest, appUserService.getUser((String) auth.getPrincipal())));
    }

    /**
     * Approves a membership request | Accessible by manager
     * @param id organization id
     * @return status
     * @throws AccessDeniedException if organization id is invalid
     * @throws ResourceNotFound if the request user in not the membership request's organizer's manager
     */
    @Operation(summary = "Approves membership request")
    @ApiResponse(responseCode = "200", description = "Returns status")
    @PutMapping("/membership/approve/{id}")
    public ResponseEntity<Map<String, Boolean>> approveMembership(@PathVariable Long id) throws AccessDeniedException, ResourceNotFound {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", membershipService.changeMembershipApproval(id, appUserService.getUser((String) auth.getPrincipal()), true));
        return ResponseEntity.ok().body(status) ;
    }

    /**
     * Returns approved membership | Accessible by all
     * @param organizationId organization id
     * @param page page no
     * @param size page size
     * @param sortBy sort by field
     * @return List of MembershipResponse
     * @throws ResourceNotFound if organizationId is invalid
     */
    @Operation(summary = "Returns approved membership")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns approved memberships",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MembershipResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "Invalid token"),
    })
    @GetMapping("/membership/approved/{organizationId}")
    public ResponseEntity<List<MembershipResponse>> getApprovedMembershipRequests(
            @PathVariable Long organizationId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws ResourceNotFound {
        return ResponseEntity.ok().body(membershipService.getMembership(organizationId, true, false, page, size, sortBy));
    }

    /**
     * Returns unapproved membership | Accessible by all
     * @param organizationId organization id
     * @param page page no
     * @param size page size
     * @param sortBy sort by field
     * @return List of MembershipResponse
     * @throws ResourceNotFound if organizationId is invalid
     */
    @Operation(summary = "Returns unapproved membership")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns unapproved memberships",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MembershipResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "Invalid token"),
    })
    @GetMapping("/membership/unapproved/{organizationId}")
    public ResponseEntity<List<MembershipResponse>> getUnapprovedMembershipRequests(
            @PathVariable Long organizationId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws ResourceNotFound {
        return ResponseEntity.ok().body(membershipService.getMembership(organizationId, false, true, page, size, sortBy));
    }
}
