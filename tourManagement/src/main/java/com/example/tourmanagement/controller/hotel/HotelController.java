package com.example.tourmanagement.controller.hotel;

import com.example.tourmanagement.dto.HotelDTO;
import com.example.tourmanagement.dto.HotelReviewDTO;
import com.example.tourmanagement.service.hotel.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelsService;

    @GetMapping
    public Iterable<?> getAllHotels() {

        return hotelsService.getAllHotels();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public HotelDTO createHotel(@RequestBody HotelDTO hotel) {

        return hotelsService.createHotel(hotel);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Long id) {

        hotelsService.deleteHotel(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public HotelDTO patchHotel(@RequestBody HotelDTO hotelDTO, @PathVariable Long id) {

        return hotelsService.patchHotels(hotelDTO, id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/images")
    public void uploadHotelImage(@RequestPart MultipartFile image, @PathVariable Long id) {

        hotelsService.uploadHotelImage(id, image);
    }

    @GetMapping("/{id}")
    public HotelDTO getHotelById(@PathVariable Long id) {

        return hotelsService.getHotelById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/images")
    public void deleteHotelImage(@RequestParam String url, @PathVariable Long id) {

        hotelsService.deleteHotelImage(id, url);
    }

}
