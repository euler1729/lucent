package com.lucent.backend.api;

import com.lucent.backend.api.Exception.DuplicatePhoneException;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.*;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Organization;
import com.lucent.backend.service.AppUserService;
import com.lucent.backend.service.ImageService;
import com.lucent.backend.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data @AllArgsConstructor
class OrganizationRegistrationForm{
    private String phone, name, password, orgName, orgDescription;
    private MultipartFile image;
}

@Data @AllArgsConstructor
class OrganizationUpdateForm{
    private String description;
    Boolean autoApprove, requireNID, requireCode;
}

@RestController @RequiredArgsConstructor @Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;
    private final AppUserService appUserService;

    private final ImageService imageService;

    /**
     * Retrieve site url domain
     * @param request A HttpServletRequest object
     * @return A sting literal : siteurl
     */
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


    /**
     * Registers an organization with its manager
//     * @param form OrganizationRegistrationForm object - Consists of phone, name, password, orgName, orgDescription;
     * @param request A HttpServletRequest object
     * @return Saved User Response
     * @throws DuplicatePhoneException if user already exists with given email
     */
    @Operation(summary = "Register Organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns profile",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrganizationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Duplicate Phone Number"),
    })
    @PostMapping("/org/registration")
    public ResponseEntity<OrganizationResponse> registerOrg(
            @RequestParam("orgName") String orgName,
            @RequestParam("orgDescription") String orgDescription,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("profilePic") MultipartFile profilePic,
            @RequestParam("coverPic") MultipartFile coverPic,
            HttpServletRequest request) throws DuplicatePhoneException, IOException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/org/registration").toUriString());

        //        Create an account for manager
        AppUserRequest appUserRequest = new AppUserRequest();
        appUserRequest.setPhone(phone);
        appUserRequest.setName(name);
        appUserRequest.setPassword(password);
        AppUserResponse savedUserResponse = appUserService.saveUser(appUserRequest, appUserService.getRole("ROLE_MANAGER"), this.getSiteURL(request));
        AppUser savedUser = appUserService.getUser(savedUserResponse.phone);

        //        Create the organization
        OrganizationRequest organizationRequest = new OrganizationRequest();
        organizationRequest.setName(orgName);
        organizationRequest.setDescription(orgDescription);

        organizationRequest.setProfilePic(profilePic.getBytes());
        organizationRequest.setProfilePicName(profilePic.getOriginalFilename());
        organizationRequest.setProfilePicType(profilePic.getContentType());

        organizationRequest.setCoverPic(coverPic.getBytes());
        organizationRequest.setCoverPicName(coverPic.getOriginalFilename());
        organizationRequest.setCoverPicType(coverPic.getContentType());
        OrganizationResponse savedOrganization = organizationService.saveOrganization(organizationRequest, savedUser, this.getSiteURL(request));

        return ResponseEntity.created(uri).body(savedOrganization);
    }

    /**
     * Registers an organization with its manager given the id
     * @return Saved User Response
     * @throws DuplicatePhoneException if user already exists with given email
     */
    @Operation(summary = "Returns Organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns Organization",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrganizationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID"),
    })
    @GetMapping("/org/det/{id}")
    public ResponseEntity<OrganizationResponse> getOrg(@PathVariable Long id) throws DuplicatePhoneException, ResourceNotFound {
        return ResponseEntity.ok().body(organizationService.orgDetails(id));
    }

    /**
     * Returns Organization of manager
     * @return OrganizationResponse
     * @throws ResourceNotFound if no  Organization if found under user
     */
    @Operation(summary = "Returns Organization of manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns Organization of manager",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrganizationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID"),
    })
    @GetMapping("/org/find")
    public ResponseEntity<OrganizationResponse> getOrganizationByManager() throws ResourceNotFound {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  ResponseEntity.ok().body(organizationService.findOrganization((appUserService.getUser((String) auth.getPrincipal()))));
    }


    /**
     * Returns all published organizations
     * @param page Page no
     * @param size Page size
     * @param sortBy Sort By field
     * @return Paged Published Organizations
     */
    @Operation(summary = "Published Organizations")
    @ApiResponse(responseCode = "200", description = "Returns Published Organizations | Open for all")
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
    @Operation(summary = "All Organizations")
    @ApiResponse(responseCode = "200", description = "Returns Published Organizations | Open for admin only")
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
    @Operation(summary = "Publishes Organizations")
    @ApiResponse(responseCode = "200", description = "Publishes Organizations | Open for admin only")
    @PostMapping("/org/publish/{id}")
    public ResponseEntity<Map<String, Boolean>> publishOrg(@PathVariable Long id) throws ResourceNotFound {
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", organizationService.publishOrganization(id));
        return ResponseEntity.ok().body(status);
    }


    /**
     * Bans / Unpublishes an organization Given the id | Accessible by Admin only
     * @param id Organization id
     * @return Status
     * @throws ResourceNotFound if id is invalid
     */
    @Operation(summary = "Publishes Organizations")
    @ApiResponse(responseCode = "200", description = "Publishes Organizations | Open for admin only")
    @PostMapping("/org/unpublish/{id}")
    public ResponseEntity<Map<String, Boolean>> unpublishOrg(@PathVariable Long id) throws ResourceNotFound {
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", organizationService.unpublishOrganization(id));
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
    @Operation(summary = "Updates Organizations")
    @ApiResponse(responseCode = "200", description = "Updates Organizations | Open for manager only")
    @PutMapping("/org/update/{id}")
    public ResponseEntity<Map<String, Boolean>> updateOrg(@PathVariable Long id, @RequestBody OrganizationUpdateForm form) throws ResourceNotFound, AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", organizationService.updateOrg(id, (String) auth.getPrincipal(), form.getDescription(), form.getAutoApprove(), form.getRequireCode(), form.getRequireNID()));
        return ResponseEntity.ok().body(status);
    }

    @Operation(summary = "My Organizations")
    @ApiResponse(responseCode = "200", description = "My Organizations | Open for donor and manager only")
    @GetMapping("/org/my")
    public ResponseEntity<List<OrganizationResponse>> myOrganizations(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(organizationService.myOrganization(appUserService.getUser((String) auth.getPrincipal()), page, size, sortBy));
    }
}
