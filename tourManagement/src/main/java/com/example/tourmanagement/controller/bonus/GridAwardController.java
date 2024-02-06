package com.example.tourmanagement.controller.bonus;

import com.example.tourmanagement.dto.GridAwardDTO;
import com.example.tourmanagement.service.bonus.GridAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/grid-awards")
@RequiredArgsConstructor
public class GridAwardController {

    private final GridAwardService gridAwardsService;

    @GetMapping
    public ResponseEntity<?> getAllGridAwards() {

        Iterable<GridAwardDTO> gridAwardsList = gridAwardsService.getAllGridAwards();
        if (gridAwardsList.iterator().hasNext())
            return ResponseEntity.ok(gridAwardsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GridAwardDTO createGridAwards(@RequestBody GridAwardDTO gridAwardRequest) {
        return gridAwardsService.createGridAward(gridAwardRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteGridAwards(@PathVariable Long id) {

        gridAwardsService.deleteGridAward(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public GridAwardDTO patchGridAwards(@RequestBody GridAwardDTO gridAwardRequest, @PathVariable Long id) {

        return gridAwardsService.patchGridAwards(gridAwardRequest, id);
    }


}
