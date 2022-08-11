package com.lucent.backend.Repo;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Membership;
import com.lucent.backend.domain.Organization;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepo extends PagingAndSortingRepository<Membership, Long> {

    List<Membership> findAllByOrganization(Organization Organization);
    List<Membership> findAllByOrganizationAndApprovedIsTrue(Organization Organization);
    List<Membership> findAllByOrganizationAndApprovedIsFalse(Organization Organization);

    Optional<Membership> findByDonorAndOrganization(AppUser donor, Organization organization);

    @Modifying @Transactional
    @Query("UPDATE Membership set approved=:approved where id=:id")
    void changeApproval(Boolean approved, Long id);
}
