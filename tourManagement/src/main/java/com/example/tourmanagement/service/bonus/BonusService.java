package com.example.tourmanagement.service.bonus;

import com.example.tourmanagement.dto.response.BonusResponse;
import com.example.tourmanagement.model.entity.Bonus;

public interface BonusService {
    BonusResponse getMyBonuses(int year, int month);

    Bonus createBonus(Bonus bonus);
}
