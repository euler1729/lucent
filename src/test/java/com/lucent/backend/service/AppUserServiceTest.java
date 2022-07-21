package com.lucent.backend.service;

import com.lucent.backend.Repo.AppUserRepo;
import com.lucent.backend.Repo.RoleRepo;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import org.hibernate.mapping.Any;
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
    void setRoleRepo(){
        AppUser testUser = new AppUser();
        testUser.setName("test user");
        testUser.setEmail("test@email.com");
        testUser.setPassword("password");

        when(appUserRepo.findAppUserByEmail("test@email.com")).thenReturn(testUser);
        when(appUserRepo.save(any(AppUser.class))).thenReturn(testUser);

        Role testRole = new Role();
        testRole.setName("TESTROLE");
        when(roleRepo.findRoleByName("TESTROLE")).thenReturn(testRole);
        when(roleRepo.save(any(Role.class))).thenReturn(testRole);
    }

    @Test
    void testRegisterUser(){
        AppUser testUser = new AppUser();
        testUser.setName("test user");
        testUser.setEmail("test@email.com");
        testUser.setPassword("password");

        AppUser savedUser = appUserService.registerUser(testUser);
        assertEquals(testUser.getEmail(), savedUser.getEmail());
    }

    @Test
    void testSaveRole(){
       Role role = new Role();
       role.setName("TESTROLE");

       Role savedRole = appUserService.saveRole(role);
       assertEquals(savedRole.getName(), roleRepo.findRoleByName("TESTROLE").getName());
    }

}