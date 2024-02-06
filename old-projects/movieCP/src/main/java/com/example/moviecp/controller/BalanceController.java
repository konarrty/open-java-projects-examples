package com.example.moviecp.controller;

import com.example.moviecp.model.Balance;
import com.example.moviecp.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balances")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Balance>> getAllBalances() {

        return ResponseEntity.ok(balanceService.getAllBalances());
    }

    @PostMapping("/")
    public ResponseEntity<Balance> createBalance(@RequestBody Balance balance) {

        return ResponseEntity.ok(balanceService.createBalance(balance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Balance> putBalance(@RequestBody Balance balance, @PathVariable Long id) {

        return ResponseEntity.ok(balanceService.putBalance(balance, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable Long id) {

        balanceService.deleteBalance(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Balance> getBalanceById(@PathVariable Long id) {

        return ResponseEntity.ok(balanceService.getBalanceById(id));
    }


}
