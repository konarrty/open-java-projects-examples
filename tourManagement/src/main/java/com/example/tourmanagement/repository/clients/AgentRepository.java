package com.example.tourmanagement.repository.clients;

import com.example.tourmanagement.model.entity.Agent;
import com.example.tourmanagement.model.entity.Reservation;
import com.example.tourmanagement.model.entity.TourOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findAgentByUserUsername(String username);

    @Query("""
            select r from Reservation  r
                        where r.agent.id = :agentId
            """)
    Iterable<Reservation> findAgentReservations(Long agentId);

    Iterable<Agent> findAllByOperator(TourOperator operator);
}
