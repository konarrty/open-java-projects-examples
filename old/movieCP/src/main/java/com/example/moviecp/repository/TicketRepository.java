package com.example.moviecp.repository;

import com.example.moviecp.model.Film;
import com.example.moviecp.model.Ticket;
import com.example.moviecp.service.TicketService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findTicketById(Long id);

}
