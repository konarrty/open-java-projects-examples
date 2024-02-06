package com.example.tourmanagement.repository.bonus;

import com.example.tourmanagement.model.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, Long> {
    Bonus findByAgentIdAndYearAndMonth(Long agentID, int year, int month);
}
