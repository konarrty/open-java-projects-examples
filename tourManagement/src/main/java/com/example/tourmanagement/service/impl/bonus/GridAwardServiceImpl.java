package com.example.tourmanagement.service.impl.bonus;

import com.example.tourmanagement.dto.GridAwardDTO;
import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.mapper.bonus.GridAwardMapper;
import com.example.tourmanagement.model.entity.GridAward;
import com.example.tourmanagement.repository.bonus.GridAwardRepository;
import com.example.tourmanagement.service.bonus.GridAwardService;
import com.example.tourmanagement.service.tour.ReservationService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GridAwardServiceImpl implements GridAwardService {

    private final GridAwardRepository gridAwardRepository;

    private final ReservationService reservationService;
    private final GridAwardMapper gridAwardMapper;

    @Override
    public Iterable<GridAwardDTO> getAllGridAwards() {
        Iterable<GridAward> grids = gridAwardRepository.findAllByTourOperatorId(
                ClientContextUtils.getOperator().getId()
        );

        return gridAwardMapper.toDTO(grids);
    }

    @Override
    public GridAwardDTO createGridAward(GridAwardDTO gridAwardRequest) {
        GridAward grid = gridAwardMapper.toEntity(gridAwardRequest);
        var operator = ClientContextUtils.getOperator();

        if (!gridAwardRepository.isValidGridAward(grid.getFactor(), grid.getVolume(),
                operator.getId()))
            throw new LogicException("Несогласованное значение факторов");

        grid.setTourOperator(operator);
        gridAwardRepository.save(grid);

        return gridAwardMapper.toDTO(grid);
    }


    @Override
    public GridAwardDTO patchGridAwards(GridAwardDTO newGridAward, Long id) {
        var operator = ClientContextUtils.getOperator();
        GridAward grid = gridAwardRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Сетка премий не найдена!"));

        gridAwardMapper.partialUpdate(grid, newGridAward);

        if (!gridAwardRepository.isValidPatchGridAward(grid.getFactor(), grid.getVolume(),
                operator.getId(), grid.getId()))
            throw new LogicException("Несогласованное значение факторов");
        gridAwardRepository.save(grid);

        return gridAwardMapper.toDTO(grid);
    }

    @Override
    public void deleteGridAward(Long id) {
        if (!gridAwardRepository.existsById(id))
            throw new NoSuchElementException("Сетка премий не найдена!");

        gridAwardRepository.deleteById(id);
    }

    @Override
    public GridAward getGridAwardById(Long id) {
        return gridAwardRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Сетка премий не найдена!"));
    }

    @Override
    public void createBonusesAccordingTheGrid() {
        LocalDateTime targetDateTime = LocalDateTime.now().minusMonths(1L);

        reservationService.createBonusesAccordingTheGrid(targetDateTime);
    }
}
