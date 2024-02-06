package com.example.tourmanagement.service.impl.hotel;

import com.example.tourmanagement.dto.HotelDTO;
import com.example.tourmanagement.mapper.hotel.HotelMapper;
import com.example.tourmanagement.model.entity.Hotel;
import com.example.tourmanagement.repository.hotel.HotelRepository;
import com.example.tourmanagement.service.hotel.HotelService;
import com.example.tourmanagement.utils.ClientContextUtils;
import com.example.tourmanagement.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    private final FileUtils fileUtils;

    @Override
    public Iterable<HotelDTO> getAllHotels() {
        Iterable<Hotel> hotels = hotelRepository.findAllByOperatorId(
                ClientContextUtils.getOperator().getId()
        );

        return hotelMapper.toDTO(hotels);
    }

    @Transactional
    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO) {

        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel.setOperator(ClientContextUtils.getOperator());
        hotel = hotelRepository.save(hotel);

        return hotelMapper.toDTO(hotel);
    }

    @Transactional
    @Override
    public void uploadHotelImage(Long hotelId, MultipartFile file) {

        fileUtils.saveImage(file, Hotel.class.getSimpleName().toLowerCase(), hotelId);
    }

    @Transactional
    @Override
    public void deleteHotelImage(Long hotelId, String url) {

        fileUtils.deleteImage(hotelId, Hotel.class.getSimpleName().toLowerCase(), url);
    }

    @Override
    public HotelDTO patchHotels(HotelDTO newHotel, Long id) {
        Hotel hotel = findHotelById(id);

        hotelMapper.partialUpdate(hotel, newHotel);
        hotelRepository.save(hotel);

        return hotelMapper.toDTO(hotel);
    }

    @Override
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id))
            throw new NoSuchElementException("Отель не найден!");

        hotelRepository.deleteById(id);
    }

    @Override
    public Hotel findHotelById(Long id) {
        return hotelRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Отель не найден!"));
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Отель не найден!"));

        return hotelMapper.toDTO(hotel);
    }

}
