package com.lucent.backend.Repo;

import com.lucent.backend.domain.Organization;
import com.lucent.backend.domain.Spending;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpendingRepo extends PagingAndSortingRepository<Spending, Long> {

    List<Spending> findAllByOrganization(Organization organization, Pageable pageable);
}
