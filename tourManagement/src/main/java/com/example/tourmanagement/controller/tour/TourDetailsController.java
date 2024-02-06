package com.example.tourmanagement.controller.tour;

import com.example.tourmanagement.dto.request.TourDetailsRequest;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.service.tour.TourDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/tour-details")
@RequiredArgsConstructor
public class TourDetailsController {

    private final TourDetailsService tourDetailsService;

    @GetMapping
    public ResponseEntity<?> getAllTourDetails(@RequestParam(required = false) boolean sortByMarks) {

        Iterable<TourDetailsResponse> tourDetailsList = tourDetailsService.getAllTourDetails(sortByMarks);
        if (tourDetailsList.iterator().hasNext())
            return ResponseEntity.ok(tourDetailsList);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyAllTourDetails() {

        Iterable<TourDetailsResponse> tourDetailsList = tourDetailsService.getMyAllTourDetails();
        if (tourDetailsList.iterator().hasNext())
            return ResponseEntity.ok(tourDetailsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TourDetailsResponse createTourDetails(@RequestBody TourDetailsRequest tourDetails) {

        return tourDetailsService.createTourDetails(tourDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTourDetails(@PathVariable Long id) {

        tourDetailsService.deleteTourDetails(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public TourDetailsResponse patchTourDetails(@RequestBody TourDetailsRequest tourDetailsDTO, @PathVariable Long id) {

        return tourDetailsService.patchTourDetails(tourDetailsDTO, id);
    }

    @GetMapping("/top")
    public Iterable<TourDetailsResponse> getTop5TourStat() {

        return tourDetailsService.findAllSortByAverageMark(
                PageRequest.of(0, 5)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/images")
    public TourDetailsResponse uploadDetailsImage(@RequestPart MultipartFile image, @PathVariable Long id) {

        return tourDetailsService.uploadDetailsImage(id, image);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/images")
    public void deleteHotelImage(@RequestParam String url, @PathVariable Long id) {

        tourDetailsService.deleteDetailsImage(id, url);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TourDetailsResponse getTourDetailsById(@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean hotTours) {

        return tourDetailsService.findTourById(id, hotTours);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/hotels/{hotelId}")
    public TourDetailsResponse addHotel(@PathVariable Long id, @PathVariable Long hotelId) {

        return tourDetailsService.addHotel(id, hotelId);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}/hotels/{hotelId}")
    public TourDetailsResponse deleteHotel(@PathVariable Long id, @PathVariable Long hotelId) {

        return tourDetailsService.deleteHotel(id, hotelId);
    }

}
