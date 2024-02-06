package com.example.moviecp.controller;

import com.example.moviecp.dto.CheckStatDto;
import com.example.moviecp.model.Check;
import com.example.moviecp.model.Ticket;
import com.example.moviecp.service.CheckService;
import com.example.moviecp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final CheckService checkService;

    @GetMapping("/")
    public ResponseEntity<List<Ticket>> getAllTickets() {

        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PostMapping("/")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        System.err.println(ticket);

        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> putTicket(@RequestBody Ticket ticket, @PathVariable Long id) {

        return ResponseEntity.ok(ticketService.putTicket(ticket, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {

        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {

        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PostMapping("/{id}/check")
    public ResponseEntity<?> createCheck(@PathVariable Long id, Principal principal) {
        Check check = checkService.buyTicket(id, principal);
        if (check != null)
            return ResponseEntity.ok(check);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stat")
    public List<CheckStatDto> getStatTickets() {

        return checkService.getStatChecks();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sales")
    public double createAccountAboutSales(@RequestParam(value = "toDateTime", required = false,
            defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDate toDateTime,
                                          @RequestParam(value = "fromDateTime", required = false,
                                                  defaultValue = "#{T(java.time.LocalDateTime).MIN}")
                                                  LocalDate fromDateTime) {
        System.err.println(toDateTime);
        return checkService.createAccountAboutSales(fromDateTime, toDateTime);
    }


}
