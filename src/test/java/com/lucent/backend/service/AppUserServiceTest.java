package com.lucent.backend.service;

import com.lucent.backend.Notifications.TextService;
import com.lucent.backend.Repo.AppUserRepo;
import com.lucent.backend.Repo.RoleRepo;
import com.lucent.backend.api.Exception.DuplicatePhoneException;
import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserServiceTest {
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private AppUserRepo appUserRepo;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private AppUserService appUserService;


    @BeforeEach
    void setStubs(){
        AppUser testUser = new AppUser();
        testUser.setName("test user");
        testUser.setPhone("111111111");
        testUser.setPassword("password");

        when(appUserRepo.findAppUserByPhone("111111111")).thenReturn(testUser);
        when(appUserRepo.save(any(AppUser.class))).thenReturn(testUser);

        Role testRole = new Role();
        testRole.setName("TESTROLE");
        when(roleRepo.findRoleByName("TESTROLE")).thenReturn(testRole);
        when(roleRepo.save(any(Role.class))).thenReturn(testRole);
    }

    @Test
    void testGetUserResponse(){
        AppUserResponse appUserResponse = appUserService.getUserResponse("111111111");
        assertEquals(appUserResponse.name, appUserRepo.findAppUserByPhone("111111111").getName());
    }

    @Test
    void testGetUser(){
        AppUser appUser = appUserService.getUser("111111111");
        assertEquals(appUser.getName(), appUserRepo.findAppUserByPhone("111111111").getName());
    }


    @Test
    void testSaveUser()  {

        AppUserRequest appUserRequest = new AppUserRequest();
        appUserRequest.setName("test user");
        appUserRequest.setPhone("111111110");
        appUserRequest.setPassword("password");

        Role role = new Role(0L, "TESTROLE");
        try {
            AppUserResponse appUserResponse = appUserService.saveUser(appUserRequest, role, "test");
            assertEquals(appUserResponse.name, appUserRepo.findAppUserByPhone("111111111").getName());;
        } catch (DuplicatePhoneException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveRole(){
       Role role = new Role();
       role.setName("TESTROLE");

       Role savedRole = appUserService.saveRole(role);
       assertEquals(savedRole.getName(), roleRepo.findRoleByName("TESTROLE").getName());
    }


    @Test
    void testGetRole(){
        assertEquals(appUserService.getRole("TESTROLE").getName(), "TESTROLE");
    }
}