package com.example.tourmanagement.service.impl.tour;

import com.example.tourmanagement.dto.request.TourDetailsRequest;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.mapper.tour.TourDetailsMapper;
import com.example.tourmanagement.model.entity.Hotel;
import com.example.tourmanagement.model.entity.Tour;
import com.example.tourmanagement.model.entity.TourDetails;
import com.example.tourmanagement.repository.tour.TourDetailsRepository;
import com.example.tourmanagement.service.tour.TourDetailsService;
import com.example.tourmanagement.utils.ClientContextUtils;
import com.example.tourmanagement.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourDetailsServiceImpl implements TourDetailsService {

    private final TourDetailsRepository tourDetailsRepository;

    private final TourDetailsMapper tourDetailsMapper;
    private final FileUtils fileUtils;

    @Override
    public Iterable<TourDetailsResponse> getMyAllTourDetails() {

        Iterable<TourDetails> operator = tourDetailsRepository.findAllByOperator(ClientContextUtils.getOperator());

        return tourDetailsMapper.toResponse(operator);
    }

    @Override
    public Iterable<TourDetailsResponse> findAllSortByAverageMark(PageRequest pageRequest) {
        Iterable<TourDetails> details = tourDetailsRepository.findAllSortByAverageMark(pageRequest);

        return tourDetailsMapper.toResponse(details);
    }

    @Override
    public TourDetailsResponse createTourDetails(TourDetailsRequest tourDetailsDTO) {

        TourDetails details = tourDetailsMapper.toEntity(tourDetailsDTO);
        details.setOperator(ClientContextUtils.getOperator());
        tourDetailsRepository.save(details);

        return tourDetailsMapper.toResponse(details);
    }

    @Override
    public TourDetailsResponse patchTourDetails(TourDetailsRequest newTourDetails, Long id) {
        TourDetails newTourDetailsEntity = tourDetailsMapper.toEntity(newTourDetails);

        TourDetails details = getTourDetailsById(id);

        tourDetailsMapper.partialUpdate(details, newTourDetailsEntity);
        tourDetailsRepository.save(details);

        return tourDetailsMapper.toResponse(details);
    }

    @Override
    public void deleteTourDetails(Long id) {
        if (!tourDetailsRepository.existsById(id))
            throw new NoSuchElementException("Тур не найден!");

        tourDetailsRepository.deleteById(id);
    }

    @Override
    public TourDetails getTourDetailsById(Long id) {
        return tourDetailsRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Тур не найден!"));
    }

    @Override
    public boolean existsById(Long id) {
        return tourDetailsRepository.existsById(id);
    }

    @Transactional
    @Override
    public TourDetailsResponse uploadDetailsImage(Long detailsId, MultipartFile file) {

        TourDetails details = getTourDetailsById(detailsId);
        fileUtils.saveImage(file, TourDetails.class.getSimpleName().toLowerCase(), details.getId());

        return tourDetailsMapper.toResponse(details);
    }

    @Transactional
    @Override
    public void deleteDetailsImage(Long detailsId, String url) {

        fileUtils.deleteImage(detailsId, TourDetails.class.getSimpleName().toLowerCase(), url);
    }
    @Override
    public Iterable<TourDetailsResponse> getAllTourDetails(boolean sortByMarks) {

        return tourDetailsMapper.toResponse(
                !sortByMarks
                        ? tourDetailsRepository.findAll()
                        : tourDetailsRepository.findAllSortByAverageMark(null)
        );
    }

    @Override
    public TourDetailsResponse findTourById(Long id, Boolean hotTours) {

        TourDetails tourDetails = getTourDetailsById(id);
        List<Tour> tours = tourDetails.getTours()
                .stream().filter(t -> t.getLastTimeTour().equals(hotTours)).toList();

        tourDetails.setTours(tours);

        return tourDetailsMapper.toResponse(tourDetails);
    }

    @Transactional
    @Override
    public TourDetailsResponse addHotel(Long id, Long hotelId) {

        TourDetails tourDetails = getTourDetailsById(id);
        tourDetails.setHotel(new Hotel(hotelId));

        return tourDetailsMapper.toResponse(tourDetails);
    }

    @Transactional
    @Override
    public TourDetailsResponse deleteHotel(Long id, Long hotelId) {

        TourDetails tourDetails = getTourDetailsById(id);

        if (!tourDetails.getHotel().getId().equals(hotelId))
            throw new LogicException("Данный отель не найден");
        tourDetails.setHotel(null);

        return tourDetailsMapper.toResponse(tourDetails);
    }

}
