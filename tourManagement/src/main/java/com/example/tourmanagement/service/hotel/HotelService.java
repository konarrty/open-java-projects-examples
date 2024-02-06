package com.example.tourmanagement.service.hotel;

import com.example.tourmanagement.dto.HotelDTO;
import com.example.tourmanagement.model.entity.Hotel;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface HotelService {
    Iterable<HotelDTO> getAllHotels();

    HotelDTO createHotel(HotelDTO hotel);

    @Transactional
    void uploadHotelImage(Long hotelId, MultipartFile file);

    @Transactional
    void deleteHotelImage(Long hotelId, String url);

    HotelDTO patchHotels(HotelDTO newHotel, Long id);

    void deleteHotel(Long id);

    Hotel findHotelById(Long id);
    HotelDTO getHotelById(Long id);
}
