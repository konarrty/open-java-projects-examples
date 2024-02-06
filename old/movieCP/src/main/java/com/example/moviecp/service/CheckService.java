package com.example.moviecp.service;

import com.example.moviecp.dto.CheckStatDto;
import com.example.moviecp.model.Check;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface CheckService {

    Check buyTicket(Long id, Principal principal);

    List<Check> getAllChecks();

    List<Check> getUsersChecks(Long userId);

    List<Check> getUsersChecksByPrincipal(Principal principal);

    List<CheckStatDto> getStatChecks();

    double createAccountAboutSales(LocalDate afterDateTime, LocalDate beforeDateTime);


}