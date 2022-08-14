package com.lucent.backend.service;

import com.lucent.backend.Repo.ImageRepo;
import com.lucent.backend.Repo.MembershipRepo;
import com.lucent.backend.Repo.OrganizationRepo;
import com.lucent.backend.Util.ImageUtil;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.api.dto.OrganizationRequest;
import com.lucent.backend.api.dto.OrganizationResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Image;
import com.lucent.backend.domain.Membership;
import com.lucent.backend.domain.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    ImageRepo imageRepo;

    @Autowired private MembershipRepo membershipRepo;

    /**
     * Saves an organization and adds manager
     * @param organizationRequest An OrganizationRequest object
     * @param manager An AppUser object
     * @return saved organization as OrganizationResponse
     */
    public OrganizationResponse saveOrganization(OrganizationRequest organizationRequest, AppUser manager, String siteurl){

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        String profilepicname = organizationRequest.getProfilePicName().replaceAll("\\s", "");
        String coverpicname = organizationRequest.getCoverPicName().replaceAll("\\s", "");

        profilepicname = timeStamp + profilepicname;
        coverpicname = timeStamp + coverpicname;

        Image proPic = imageRepo.save(Image.builder()
                        .name(profilepicname)
                        .image(organizationRequest.getProfilePic())
                        .type(organizationRequest.getProfilePicType())
                .build());

        Image coverPic = imageRepo.save(Image.builder()
                .name(coverpicname)
                .image(organizationRequest.getCoverPic())
                .type(organizationRequest.getCoverPicType())
                .build());

        Organization organization = new Organization();
        organization.setName(organizationRequest.getName());
        organization.setDescription(organizationRequest.getDescription());
        organization.setProfilePic(proPic);
        organization.setProfilePicURL(siteurl + "/images/" + profilepicname);
        organization.setCoverPic(coverPic);
        organization.setCoverPicURL(siteurl + "/images/" + coverpicname);
        organization.setManager(manager);

        Membership membership = Membership.builder()
                .donor(manager)
                .organization(organization)
                .approved(true)
                .build();

        Membership savedMemberhip = membershipRepo.save(membership);

        return new OrganizationResponse(organizationRepo.save(organization));
    }

    /**
     * Returns organization detail by id
     * @param id Organization id
     * @return OrganizationResponse
     * @throws ResourceNotFound if id is invalid
     */
    public OrganizationResponse orgDetails(Long id) throws ResourceNotFound {
        Optional<Organization> org = organizationRepo.findById(id);

        if(org.isPresent()) return new OrganizationResponse(org.get());
        else throw new ResourceNotFound("Invalid Id");
    }

    public OrganizationResponse findOrganization(AppUser manager) throws ResourceNotFound {
        Optional<Organization> organization = organizationRepo.findByManager(manager);

        if(organization.isPresent()) return new OrganizationResponse(organization.get());
        else throw new ResourceNotFound("No organization found");
    }

    /**
     * Returns all published organizations
     * @param onlyPublished If true returns only published organizations, else returns all
     * @param page page no
     * @param size page size
     * @param sortBy sort by field
     * @return List of Organizations as List< OrganizationResponse >
     */
    public List<OrganizationResponse> getOrganizations(Boolean onlyPublished, int page, int size, String sortBy){

        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        List<OrganizationResponse> organizationResponses = new ArrayList<>();

        if(onlyPublished) {
            organizationRepo.findAllByPublishedIsTrue(paging).forEach(organization -> {
                organizationResponses.add(new OrganizationResponse(organization));
            });
        }
        else{
            organizationRepo.findAll(paging).forEach(organization -> {
                organizationResponses.add(new OrganizationResponse(organization));
            });
        }
        return organizationResponses;
    }

    /**
     * Publishes an organization given id
     * @param id Organization id
     * @return True is succeeded
     * @throws ResourceNotFound if id is invalid
     */
    public Boolean publishOrganization(Long id) throws ResourceNotFound {

        Optional<Organization> org = organizationRepo.findById(id);
        if (org.isPresent()){
            organizationRepo.publishOrganizationByID(org.get().getId());
            return true;
        }
        else{
            throw new ResourceNotFound("Organization not found of this id.");
        }
    }

    /**
     * Unpublishes an organization given id
     * @param id Organization id
     * @return True is succeeded
     * @throws ResourceNotFound if id is invalid
     */
    public Boolean unpublishOrganization(Long id) throws ResourceNotFound {

        Optional<Organization> org = organizationRepo.findById(id);
        if (org.isPresent()){
            organizationRepo.unpublishOrganizationByID(org.get().getId());
            return true;
        }
        else{
            throw new ResourceNotFound("Organization not found of this id.");
        }
    }

    /**
     * Updates Organization information and returns updated organization as OrganizationResponse
     * @param id Organization id
    *  @param managerPhone manager phone
     * @param Description Organization Description
     * @param autoApprove Boolean
     * @param requireCode Boolean
     * @param requireNID Boolean
     * @return true if succeeded
     * @throws ResourceNotFound if is invalid
     */
    public Boolean updateOrg (
            Long id,
            String managerPhone,
            String Description,
            Boolean autoApprove,
            Boolean requireCode,
            Boolean requireNID
    ) throws ResourceNotFound, AccessDeniedException {
        Optional<Organization> org = organizationRepo.findById(id);
        if (org.isPresent()){

            if(Objects.equals(org.get().getManager().getPhone(), managerPhone)) {
                organizationRepo.updateOrg(Description, autoApprove, requireCode, requireNID, org.get().getId());
                return true;
            }
            else{
                throw new AccessDeniedException("Only manager can update settings.");
            }
        }
        else{
            throw new ResourceNotFound("Organization not found of this id.");
        }
    }

    /**
     * Returns donors organization
     * @param donor Appuser
     * @param page page no
     * @param size size
     * @param sortBy sort by field
     * @return List of OrganizationResponse
     */
    public List<OrganizationResponse> myOrganization(AppUser donor, int page, int size, String sortBy){
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        List<Membership> memberships = membershipRepo.findAllByApprovedIsTrueAndDonor(donor, paging);

        List<OrganizationResponse> organizationResponses =new ArrayList<>();

        memberships.forEach(membership -> {
            organizationResponses.add(new OrganizationResponse(membership.getOrganization()));
        });

        return organizationResponses;
    }
}
