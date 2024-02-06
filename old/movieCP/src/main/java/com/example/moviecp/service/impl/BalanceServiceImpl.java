package com.example.moviecp.service.impl;

import com.example.moviecp.model.Balance;
import com.example.moviecp.repository.BalanceRepository;
import com.example.moviecp.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    @Override
    public List<Balance> getAllBalances() {

        return balanceRepository.findAll();
    }

    @Override
    public Balance getBalanceById(Long id) {

        Optional<Balance> optionalBalance = balanceRepository.findById(id);
        Balance balance = null;

        if (optionalBalance.isPresent())
            balance = optionalBalance.get();

        return balance;
    }

    @Override
    public Balance createBalance(Balance balance) {

        return balanceRepository.save(balance);
    }

    @Override
    public Balance putBalance(Balance newBalance, Long balanceId) {
        Balance balance = getBalanceById(balanceId);
        newBalance.setId(balance.getId());

        return balanceRepository.save(newBalance);
    }

    @Override
    public void deleteBalance(Long id) {

        balanceRepository.deleteById(id);
    }


}
