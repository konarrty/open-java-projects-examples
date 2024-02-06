package com.example.tourmanagement.repository.tour;

import com.example.tourmanagement.model.entity.TourDetails;
import com.example.tourmanagement.model.entity.TourOperator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDetailsRepository extends JpaRepository<TourDetails, Long> {

    String SQL_CALCULATE_RATING_FORMULA =
            "(SELECT avg(m.value) FROM marks m where m.details_id = id)";

    @Query("SELECT t.details FROM TourMark t group by t.details order by AVG(t.value) desc ")
    Iterable<TourDetails> findAllSortByAverageMark(PageRequest pageable);

    Iterable<TourDetails> findAllByOperator(TourOperator operator);

}
