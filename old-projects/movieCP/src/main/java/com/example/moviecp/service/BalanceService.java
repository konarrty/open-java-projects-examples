package com.example.moviecp.service;

import com.example.moviecp.model.Balance;

import java.util.List;

public interface BalanceService {

    List<Balance> getAllBalances();


    Balance getBalanceById(Long id);

    Balance createBalance(Balance balanc);

    Balance putBalance(Balance balance, Long balanceId);

    void deleteBalance(Long id);


}