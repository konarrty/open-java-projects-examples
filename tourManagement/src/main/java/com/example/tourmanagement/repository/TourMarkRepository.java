package com.example.tourmanagement.repository;

import com.example.tourmanagement.model.entity.TourMark;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourMarkRepository extends JpaRepository<TourMark, Long> {

    boolean existsByClientIdAndDetailsId(Long clientId, Long detailsId);

    Iterable<TourMark> findAllByDetailsId(Long detailsId, PageRequest pageRequest);

    TourMark findAllByDetailsIdAndClientId(Long detailsId, Long clientId);

    @Query("SELECT coalesce(AVG(t.value), 0) FROM TourMark t WHERE t.details.id = :detailsId")
    Double calculateAverageMarkByDetailsId(Long detailsId);


}
