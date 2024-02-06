package com.example.moviecp.service;

import com.example.moviecp.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {

    List<Ticket> getAllTickets();

    Ticket getTicketById(Long id);

    Ticket createTicket(Ticket ticket);

    Ticket putTicket(Ticket ticket, Long ticketId);

    void deleteTicket(Long id);



}