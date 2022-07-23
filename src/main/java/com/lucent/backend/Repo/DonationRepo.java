package com.lucent.backend.Repo;

import com.lucent.backend.domain.Donation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepo extends PagingAndSortingRepository<Donation, Long> {
}
