package com.lucent.backend.Repo;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Organization;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepo extends PagingAndSortingRepository<Organization, Long> {

    List<Organization> findAllByPublishedIsTrue(Pageable pageable);
    Optional<Organization> findById(Long id);
    Optional<Organization> findByManager(AppUser manager);

    @Modifying @Transactional @Query("UPDATE Organization set published=TRUE where id=:id")
    void publishOrganizationByID(Long id);

    @Modifying @Transactional @Query("UPDATE Organization set published=FALSE where id=:id")
    void unpublishOrganizationByID(Long id);

    @Modifying @Transactional
    @Query("UPDATE Organization set description=:description ,autoApprove=:autoApprove, requireCode=:requireCode, requireNID=:requireNID where id=:id")
    void updateOrg(String description, Boolean autoApprove, Boolean requireCode, Boolean requireNID, Long id);

    @Modifying @Transactional
    @Query("UPDATE Organization set balance=:balance where id=:id")
    void setBalance(Double balance, Long id);
}
