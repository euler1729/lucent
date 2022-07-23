package com.lucent.backend.api;

import com.lucent.backend.api.Exception.DuplicatePhoneException;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.api.dto.OrganizationRequest;
import com.lucent.backend.api.dto.OrganizationResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.service.AppUserService;
import com.lucent.backend.service.OrganizationService;
import lombok.Data;
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
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
class OrganizationRegistrationForm{
    private String phone, name, password, orgName, orgDescription;
}

@Data
class OrganizationUpdateForm{
    private String description;
    Boolean autoApprove, requireNID, requireCode;
}

@RestController @RequiredArgsConstructor @Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;
    private final AppUserService appUserService;

    /**
     * Retrive site url domain
     * @param request A HttpServletRequest object
     * @return A sting literal : siteurl
     */
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


    /**
     * Registers an organization with its manager
     * @param form OrganizationRegistrationForm object - Consists of phone, name, password, orgName, orgDescription;
     * @param request A HttpServletRequest object
     * @return Saved User Response
     * @throws DuplicatePhoneException if user already exists with given email
     */
    @PostMapping("/org/registration")
    public ResponseEntity<OrganizationResponse> registerDonor(@RequestBody @Valid OrganizationRegistrationForm form, HttpServletRequest request) throws DuplicatePhoneException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/org/registration").toUriString());

        AppUserRequest appUserRequest = new AppUserRequest();
        appUserRequest.setPhone(form.getPhone());
        appUserRequest.setName(form.getName());
        appUserRequest.setPassword(form.getPassword());
        AppUserResponse savedUserResponse = appUserService.saveUser(appUserRequest, appUserService.getRole("ROLE_MANAGER"), this.getSiteURL(request));
        AppUser savedUser = appUserService.getUser(savedUserResponse.phone);

        OrganizationRequest organizationRequest = new OrganizationRequest();
        organizationRequest.setName(form.getOrgName());
        organizationRequest.setDescription(form.getOrgDescription());
        OrganizationResponse savedOrganization = organizationService.saveOrganization(organizationRequest, savedUser);

        return ResponseEntity.created(uri).body(savedOrganization);
    }

    /**
     * Returns all published organizations
     * @param page Page no
     * @param size Page size
     * @param sortBy Sort By field
     * @return Paged Published Organizations
     */
    @GetMapping("/org/published")
    public ResponseEntity<List<OrganizationResponse>> getPublishedOrg(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.ok().body(organizationService.getOrganizations(true,  page, size, sortBy));
    }

    /**
     * Returns all organizations | Accessible by Admin only
     * @param page Page no
     * @param size Page size
     * @param sortBy Sort By field
     * @return Paged All Organizations
     */
    @GetMapping("/org/all")
    public ResponseEntity<List<OrganizationResponse>> getAllOrg(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.ok().body(organizationService.getOrganizations(false, page, size, sortBy));
    }

    /**
     * Publishes an organization Given the id | Accessible by Admin only
     * @param id Organization id
     * @return Status
     * @throws ResourceNotFound if id is invalid
     */
    @PostMapping("/org/publish/{id}")
    public ResponseEntity<Map<String, Boolean>> publishOrg(@PathVariable Long id) throws ResourceNotFound {
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", organizationService.publishOrganization(id));
        return ResponseEntity.ok().body(status);
    }

    /**
     * Updates organization information | Accessible by Manager Only
     * @param id Organization id
     * @param form An OrganizationUpdateForm containing description, autoApprove, requireNID, requireCode
     * @return status
     * @throws ResourceNotFound if id is invalid
     * @throws AccessDeniedException if requesting user is not the manager of the organization
     */
    @PutMapping("/org/update/{id}")
    public ResponseEntity<Map<String, Boolean>> updateOrg(@PathVariable Long id, @RequestBody OrganizationUpdateForm form) throws ResourceNotFound, AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", organizationService.updateOrg(id, (String) auth.getPrincipal(), form.getDescription(), form.getAutoApprove(), form.getRequireCode(), form.getRequireNID()));
        return ResponseEntity.ok().body(status);
    }


}
