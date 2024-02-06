package com.example.tourmanagement.config;

import com.example.tourmanagement.service.bonus.GridAwardService;
import com.example.tourmanagement.service.tour.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final TourService tourService;
    private final GridAwardService gridService;

    @Scheduled(cron = "@daily")
    public void updateTourPriceScheduled() {

        tourService.updateLastMinuteTourGroup();
    }

    @Scheduled(cron = "@monthly")
    public void createBonusesScheduled() {

        gridService.createBonusesAccordingTheGrid();
    }
}