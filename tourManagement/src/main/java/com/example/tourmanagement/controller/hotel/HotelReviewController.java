package com.example.tourmanagement.controller.hotel;

import com.example.tourmanagement.dto.HotelReviewDTO;
import com.example.tourmanagement.service.hotel.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class HotelReviewController {

    private final HotelReviewService hotelReviewsService;

    @GetMapping
    public ResponseEntity<?> getAllHotelReviews(@RequestParam(required = false) Long hotelId) {

        Iterable<HotelReviewDTO> hotelReviewsList = hotelReviewsService.getAllHotelReviews(hotelId);
        if (hotelReviewsList.iterator().hasNext())
            return ResponseEntity.ok(hotelReviewsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public HotelReviewDTO createHotelReviews(@RequestBody HotelReviewDTO hotelReviewDTO) {

        return hotelReviewsService.createHotelReview(hotelReviewDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteHotelReviews(@PathVariable Long id) {

        hotelReviewsService.deleteHotelReview(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public HotelReviewDTO patchHotelReviews(@RequestBody HotelReviewDTO hotelReviewDTO, @PathVariable Long id) {

        return hotelReviewsService.patchHotelReviews(hotelReviewDTO, id);
    }

    @GetMapping("/{id}")
    public HotelReviewDTO getReviewById(@PathVariable Long id) {

        return hotelReviewsService.getHotelReviewById(id);
    }

}
