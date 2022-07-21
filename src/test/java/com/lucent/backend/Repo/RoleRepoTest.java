package com.lucent.backend.Repo;

import com.lucent.backend.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class RoleRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepo roleRepo;

    @Test
    public void testSaveRole(){

        Role testRole = new Role();
        testRole.setName("TESTROLE");

        Role savedRole = roleRepo.save(testRole);
        Role existedRole = entityManager.find(Role.class, savedRole.getId());

        assert(testRole.getName().equals(existedRole.getName()));
    }
}