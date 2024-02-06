package com.example.tourmanagement.service.impl.hotel;

import com.example.tourmanagement.dto.HotelReviewDTO;
import com.example.tourmanagement.mapper.hotel.HotelReviewMapper;
import com.example.tourmanagement.model.entity.HotelReview;
import com.example.tourmanagement.repository.hotel.HotelReviewRepository;
import com.example.tourmanagement.service.hotel.HotelReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelReviewServiceImpl implements HotelReviewService {

    private final HotelReviewRepository hotelReviewRepository;
    private final HotelReviewMapper hotelReviewMapper;

    @Override
    public Iterable<HotelReviewDTO> getAllHotelReviews(Long hotelId) {

        Iterable<HotelReview> hotelReviews;
        if (hotelId == null)
            hotelReviews = hotelReviewRepository.findAll();
        else
            hotelReviews = hotelReviewRepository.findAllByHotelId(hotelId);

        return hotelReviewMapper.toDTO(hotelReviews);
    }

    @Override
    public HotelReviewDTO createHotelReview(HotelReviewDTO hotelReviewDTO) {
        HotelReview review = hotelReviewMapper.toEntity(hotelReviewDTO);
        hotelReviewRepository.save(review);

        return hotelReviewMapper.toDTO(review);
    }

    @Override
    public HotelReviewDTO patchHotelReviews(HotelReviewDTO newHotelReview, Long id) {
        HotelReview review = findHotelReviewById(id);
        hotelReviewMapper.partialUpdate(review, newHotelReview);
        hotelReviewRepository.save(review);

        return hotelReviewMapper.toDTO(review);
    }

    @Override
    public void deleteHotelReview(Long id) {
        if (!hotelReviewRepository.existsById(id))
            throw new NoSuchElementException("Ревью не найденj!");

        hotelReviewRepository.deleteById(id);
    }

    @Override
    public HotelReview findHotelReviewById(Long id) {

        return hotelReviewRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ревью не найдено!"));
    }

    @Override
    public HotelReviewDTO getHotelReviewById(Long id) {
        HotelReview review = hotelReviewRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ревью не найдено!"));

        return hotelReviewMapper.toDTO(review);
    }


}
