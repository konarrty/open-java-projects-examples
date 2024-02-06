package com.example.tourmanagement.repository.clients.operator;

import com.example.tourmanagement.model.entity.TourOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourOperatorRepository extends JpaRepository<TourOperator, Long>,
        StatisticRepository, GrowthStatisticRepository {

}
