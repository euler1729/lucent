package com.lucent.backend.service;

import com.lucent.backend.Repo.OrganizationRepo;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.OrganizationRequest;
import com.lucent.backend.api.dto.OrganizationResponse;
import com.lucent.backend.domain.AppUser;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    /**
     * Saves an organization and adds manager
     * @param organizationRequest An OrganizationRequest object
     * @param manager An AppUser object
     * @return saved organization as OrganizationResponse
     */
    public OrganizationResponse saveOrganization(OrganizationRequest organizationRequest, AppUser manager){

        Organization organization = new Organization();
        organization.setName(organizationRequest.getName());
        organization.setDescription(organizationRequest.getDescription());
        organization.setManager(manager);

        return new OrganizationResponse(organizationRepo.save(organization));
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

    public Boolean publishOrganization(Long id) throws ResourceNotFound {

        Optional<Organization> org = organizationRepo.findById(id);
        if (org.isPresent()){
            organizationRepo.publishOrganizationByID(org.get().getId());
            return true;
        }
        else{
            throw new ResourceNotFound("Organization not found if this id.");
        }
    }
}
