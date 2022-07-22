package com.lucent.backend.Repo;

import com.lucent.backend.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByEmail(String email);

    @Modifying @Transactional
    @Query("UPDATE AppUser set verified=TRUE where email=?1")
    void verifyAppUserByEmail(String email);
}
