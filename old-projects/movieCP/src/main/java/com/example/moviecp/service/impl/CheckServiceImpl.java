package com.example.moviecp.service.impl;

import com.example.moviecp.dto.CheckStatDto;
import com.example.moviecp.model.Balance;
import com.example.moviecp.model.Check;
import com.example.moviecp.model.Ticket;
import com.example.moviecp.model.User;
import com.example.moviecp.repository.CheckRepository;
import com.example.moviecp.service.CheckService;
import com.example.moviecp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;

    private final TicketService ticketService;
    private final UserServiceImpl userService;

    @Override
    public Check buyTicket(Long ticketId, Principal principal) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.findByUsername(principal.getName());
        Balance balance = user.getBalance();
        System.err.println(balance.getTotal());

        double newTotal = balance.getTotal() - ticket.getCoast();
        if (newTotal < 0)
            return null;

        balance.setTotal(newTotal);
        ticket.setFree(false);

        return checkRepository.save(new Check(ticket, user));

    }

    @Override
    public List<Check> getUsersChecks(Long userId) {

        return checkRepository.findAll().stream()
                .filter(check -> check.getViewer().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<CheckStatDto> getStatChecks() {
        List<CheckStatDto> checkStatDtoList = new ArrayList<>();

        checkRepository.findAll().stream()
                .collect(groupingBy(check ->
                        check.getTicket().getSchedule().getFilm().getName(), counting()))
                .forEach((stat, amount) ->
                        checkStatDtoList.add(new CheckStatDto(stat, amount)));

        return checkStatDtoList;
    }

    @Override
    public List<Check> getUsersChecksByPrincipal(Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return checkRepository.findAll().stream()
                .filter(check -> check.getViewer().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public double createAccountAboutSales(LocalDate fromDateTime, LocalDate toDateTime) {

        return getAllChecks().stream()
                .filter(check -> check.getTicket().getSchedule().getStartDateAndTime().isBefore(LocalDateTime.of(toDateTime, LocalTime.MIN))
                        && check.getTicket().getSchedule().getStartDateAndTime().isAfter(LocalDateTime.of(fromDateTime, LocalTime.MIN)))
                .mapToDouble(check -> check.getTicket().getCoast())
                .sum();
    }

    @Override
    public List<Check> getAllChecks() {

        return checkRepository.findAll();
    }


}
