package com.lucent.backend.service;

import com.lucent.backend.Repo.AppUserRepo;
import com.lucent.backend.Repo.OrganizationRepo;
import com.lucent.backend.Repo.RoleRepo;
import com.lucent.backend.api.dto.OrganizationRequest;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrganizationServiceTest {
    @Mock
    private OrganizationRepo organizationRepo;
    @InjectMocks
    private OrganizationService organizationService;

    @BeforeEach
    void setStubs(){

        AppUser manager = new AppUser();
        manager.setName("manager");

        Organization organization = new Organization();
        organization.setName("Organization");
        organization.setDescription("Description");
        organization.setManager(manager);

        when(organizationRepo.save(any(Organization.class))).thenReturn(organization);
    }

    @Test
    void saveOrganization() {

        AppUser manager = new AppUser();
        manager.setName("manager");

        OrganizationRequest organization = new OrganizationRequest();
        organization.setName("Organization");
        organization.setDescription("Description");

//        assertEquals(organizationService.saveOrganization(organization, manager).name, organization.getName());
    }

    @Test
    void getOrganizations() {
    }

    @Test
    void publishOrganization() {
    }

    @Test
    void updateOrg() {
    }
}