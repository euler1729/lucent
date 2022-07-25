package com.lucent.backend.Repo;

import com.lucent.backend.domain.Donation;
import com.lucent.backend.domain.Organization;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepo extends PagingAndSortingRepository<Donation, Long> {
    Iterable<Donation> getFirstByOrganizationAndAvailableIsGreaterThan(Organization organization, Double available);

    List<Donation> findAllByOrganization(Organization organization, Pageable pageable);
}
