package com.example.tourmanagement.controller.bonus;

import com.example.tourmanagement.dto.response.BonusResponse;
import com.example.tourmanagement.service.bonus.BonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/bonuses")
@RequiredArgsConstructor
public class BonusController {

    private final BonusService bonusService;

    @GetMapping
    public BonusResponse getMyBonus(@RequestParam int year, @RequestParam int month) {

        return bonusService.getMyBonuses(year, month);
    }
}
