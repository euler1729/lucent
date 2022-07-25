package com.lucent.backend.Repo;

import com.lucent.backend.domain.DonationCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationCollectionRepo extends JpaRepository<DonationCollection, Long> {
}
