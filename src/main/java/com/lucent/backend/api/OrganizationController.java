package com.lucent.backend.api;

import com.lucent.backend.api.Exception.DuplicateEmailException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
@Data
class OrganizationRegistrationForm{
    private String phone, name, password, orgName, orgDescription;
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
     * @throws DuplicateEmailException if user already exists with given email
     */
    @PostMapping("/org/registration")
    public ResponseEntity<OrganizationResponse> registerDonor(@RequestBody @Valid OrganizationRegistrationForm form, HttpServletRequest request) throws DuplicateEmailException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/org/registration").toUriString());

        AppUserRequest appUserRequest = new AppUserRequest();
        appUserRequest.setPhone(form.getPhone());
        appUserRequest.setName(form.getName());
        appUserRequest.setPassword(form.getPassword());
        AppUserResponse savedUserResponse = appUserService.saveUser(appUserRequest, this.getSiteURL(request));
        AppUser savedUser = appUserService.getUser(savedUserResponse.phone);

        OrganizationRequest organizationRequest = new OrganizationRequest();
        organizationRequest.setName(form.getOrgName());
        organizationRequest.setDescription(form.getOrgDescription());
        OrganizationResponse savedOrganization = organizationService.saveOrganization(organizationRequest, savedUser);

        return ResponseEntity.created(uri).body(savedOrganization);
    }
}
