package com.example.moviecp.service.impl;

import com.example.moviecp.model.Ticket;
import com.example.moviecp.repository.TicketRepository;
import com.example.moviecp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public List<Ticket> getAllTickets() {

        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicketById(Long id) {

        return ticketRepository.findTicketById(id);
    }

    @Override
    public Ticket createTicket(Ticket ticket) {

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket putTicket(Ticket newTicket, Long ticketId) {
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        newTicket.setId(ticket.getId());

        return ticketRepository.save(newTicket);
    }

    @Override
    public void deleteTicket(Long id) {

        ticketRepository.deleteById(id);
    }



}
