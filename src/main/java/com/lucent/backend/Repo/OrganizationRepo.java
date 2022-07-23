package com.lucent.backend.Repo;

import com.lucent.backend.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {

    List<Organization> findAllByPublishedIsTrue();

    @Modifying @Transactional @Query("UPDATE Organization set published=TRUE where id=?1")
    void publishOrganizationByID(Long id);
}
