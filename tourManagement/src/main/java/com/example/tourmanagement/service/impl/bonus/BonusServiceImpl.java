package com.example.tourmanagement.service.impl.bonus;

import com.example.tourmanagement.dto.response.BonusResponse;
import com.example.tourmanagement.mapper.bonus.BonusMapper;
import com.example.tourmanagement.model.entity.Bonus;
import com.example.tourmanagement.repository.bonus.BonusRepository;
import com.example.tourmanagement.service.bonus.BonusService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;
    private final BonusMapper bonusMapper;

    @Override
    public BonusResponse getMyBonuses(int year, int month) {
        Bonus bonus = bonusRepository.findByAgentIdAndYearAndMonth(ClientContextUtils.getAgent().getId(), year, month);

        return bonusMapper.toResponse(bonus);
    }

    @Override
    public Bonus createBonus(Bonus bonus) {

        return bonusRepository.save(bonus);

    }
}
