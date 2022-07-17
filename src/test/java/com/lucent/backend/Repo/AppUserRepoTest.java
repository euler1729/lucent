package com.lucent.backend.Repo;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class AppUserRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Test
    public void testCreateUser(){
        Role testRole = new Role();
        testRole.setName("TESTROLE");
        Role savedRole = roleRepo.save(testRole);

        AppUser user = new AppUser();
        user.setEmail("test@email.com");
        user.setName("Test User");
        user.setPassword("1234");
        user.setRole(savedRole);

        AppUser savedUser = appUserRepo.save(user);
        AppUser existedUser = entityManager.find(AppUser.class, savedUser.getId());

        assert(user.getName().equals(existedUser.getName()));
    }
}