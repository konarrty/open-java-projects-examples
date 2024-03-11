package com.example.tourmanagement.repository.tour;

import com.example.tourmanagement.model.entity.QTour;
import com.example.tourmanagement.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long>,
        JpaSpecificationExecutor<Tour>
        , QuerydslPredicateExecutor<Tour>
        , QuerydslBinderCustomizer<QTour> {

    @Override
    default void customize(QuerydslBindings bindings, QTour root) {

        bindings.excluding(root.reservations);
    }

    String SQL_CALCULATE_PRICE_FORMULA =
            "(select case when last_time_tour = true then d.price * 0.9 else d.price end from tour_details d where d.id = details_id)";

    @Modifying
    @Query("update Tour set capacity = capacity - 1 where id = :id")
    void decrementCapacity(Long id);

    @Modifying
    @Query("update Tour set capacity = capacity + 1 where id = :id")
    void incrementCapacity(Long id);

    @Modifying
    @Query("""
            update Tour t 
            set t.lastTimeTour = true
            where t.lastTimeTour = false and t.startDateTime <= :targetDate
            """)
    void findAllByLastTimeTourAndStartDateTimeIsLessThanEqual(LocalDateTime targetDate);

}
