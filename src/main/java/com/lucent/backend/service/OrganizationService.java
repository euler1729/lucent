package com.lucent.backend.service;

import com.lucent.backend.Repo.OrganizationRepo;
import com.lucent.backend.api.dto.OrganizationRequest;
import com.lucent.backend.api.dto.OrganizationResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    public OrganizationResponse saveOrganization(OrganizationRequest organizationRequest, AppUser manager){

        Organization organization = new Organization();
        organization.setName(organizationRequest.getName());
        organization.setDescription(organizationRequest.getDescription());
        organization.setManager(manager);

        return new OrganizationResponse(organizationRepo.save(organization));
    }

    public List<OrganizationResponse> getOrganizations(){
        List<OrganizationResponse> organizationResponses = new ArrayList<>();
        organizationRepo.findAll().forEach(organization -> {
            organizationResponses.add(new OrganizationResponse(organization));
        });
        return organizationResponses;
    }
}
