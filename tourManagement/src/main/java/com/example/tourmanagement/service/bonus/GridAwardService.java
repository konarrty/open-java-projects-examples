package com.example.tourmanagement.service.bonus;


import com.example.tourmanagement.dto.GridAwardDTO;
import com.example.tourmanagement.model.entity.GridAward;

public interface GridAwardService {
    Iterable<GridAwardDTO> getAllGridAwards();

    GridAwardDTO createGridAward(GridAwardDTO request);

    GridAwardDTO patchGridAwards(GridAwardDTO newGridAward, Long id);

    void deleteGridAward(Long id);

    GridAward getGridAwardById(Long id);

    void createBonusesAccordingTheGrid();
}
