package com.lucent.backend.Repo;

import com.lucent.backend.domain.Spending;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingRepo extends PagingAndSortingRepository<Spending, Long> {
}
