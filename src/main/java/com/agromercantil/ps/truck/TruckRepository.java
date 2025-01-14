package com.agromercantil.ps.truck;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TruckRepository extends PagingAndSortingRepository<TruckModel, Long> {
    Page<TruckModel> findAll(Pageable pageable);

    TruckModel save(TruckModel truck);

    Optional<TruckModel> findById(Long id);
}
