package com.example.tourmanagement.repository.regression;

import com.example.tourmanagement.dto.stat.ReservationByAgentAndMonthDTO;
import com.example.tourmanagement.dto.stat.ReservationByAgentDTO;
import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId>
        , QuerydslPredicateExecutor<Reservation>
        , QuerydslBinderCustomizer<QReservation> {

    @Override
    default void customize(QuerydslBindings bindings, QReservation root) {

        bindings.excluding(root.client);
    }
    Iterable<Reservation> findAllByClientId(Long clientId);

    Iterable<Reservation> findAllByTourId(Long tourId);

    Reservation findByTourIdAndClientId(Long tourId, Long clientId);

    Iterable<Reservation> findAllByTourDetailsOperatorId(Long operatorId);

    Iterable<Reservation> findAllByTourDetailsOperatorIdAndStatus(Long operatorId, ReservationStatus status);

    @Query("SELECT new com.example.tourmanagement.dto.stat.ReservationByAgentDTO (r.agent,SUM(r.tour.details.price)) " +
           "FROM Reservation r WHERE r.createdDateTime > :dateTime GROUP BY r.agent")
    Iterable<ReservationByAgentDTO> findAllByAgentIdAndCreatedDateTimeAfter(LocalDateTime dateTime);

    @Modifying
    @Transactional
    @Query(value = """
                   insert into bonuses(month, year, agent_id, grid_award_id)
            select extract(month from now()),
                   extract(year from now()),
                   reservation_sum.agent.id          as agent_id,
                   grid_awards.id as grid_award_id
            from grid_awards,
                 (SELECT agent , SUM(td.price) as sum
                  FROM reservations r
                           join public.tours t on t.id = r.tour_id
                           join public.agents agent on agent.id = r.agent_id
                           join tour_details td on t.details_id = td.id
                  WHERE r.created_date_time > :dateTime
                  GROUP BY agent) as reservation_sum
            where factor = (select max(factor) from grid_awards g 
            where g.volume <= reservation_sum.sum and agent.operator_id = g.tour_operator_id)
                   """, nativeQuery = true)
    void createBonusesAccordingTheGrid(LocalDateTime dateTime);

    @Query(value = """
            SELECT new com.example.tourmanagement.dto.stat.ReservationByAgentAndMonthDTO(SUM(r.tour.details.price),
             date_trunc('month', r.createdDateTime))
            FROM Reservation r
            WHERE r.agent.id = :agentId
            GROUP BY date_trunc('month', r.createdDateTime)
            """)
    List<ReservationByAgentAndMonthDTO> sumByAgentIdForYear(Long agentId);

    @Query("""
            select coalesce(sum(excursion.price),0) from ExcursionReservation 
            where client.id = :clientId and tour.id = :tourId
            """)
    double sumPricesByClientIdAndTourId(Long clientId, Long tourId);

}
