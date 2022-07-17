package com.lucent.backend.Repo;

import com.lucent.backend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
