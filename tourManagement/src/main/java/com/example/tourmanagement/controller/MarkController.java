package com.example.tourmanagement.controller;

import com.example.tourmanagement.dto.TourMarkRequest;
import com.example.tourmanagement.dto.response.TourMarkResponse;
import com.example.tourmanagement.service.TourMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkController {

    private final TourMarkService tourMarksService;

    @GetMapping
    public ResponseEntity<?> getAllMarks(@RequestParam(required = false) Long detailsId,
                                         @RequestParam(required = false, defaultValue = "5") Integer size) {

        Iterable<TourMarkResponse> tourMarks = tourMarksService.getAllMarksByDetails(detailsId, size);
        if (tourMarks.iterator().hasNext())
            return ResponseEntity.ok(tourMarks);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TourMarkResponse createTourMark(@RequestBody TourMarkRequest tourMark) {

        return tourMarksService.createMark(tourMark);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{markId}")
    public void deleteTourMark(@PathVariable Long markId) {

        tourMarksService.deleteMark(markId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{markId}")
    public TourMarkResponse patchTourMark(@RequestBody TourMarkRequest tourMarkRequest, @PathVariable Long markId) {

        return tourMarksService.patchMarks(tourMarkRequest, markId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public TourMarkResponse getMyMarkByDetailsId(@RequestParam Long detailsId) {

        return tourMarksService.getMyMarkByDetailsId(detailsId);
    }
}
