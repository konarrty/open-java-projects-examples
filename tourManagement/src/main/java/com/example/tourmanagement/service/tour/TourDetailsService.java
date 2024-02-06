package com.example.tourmanagement.service.tour;

import com.example.tourmanagement.dto.request.TourDetailsRequest;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.model.entity.TourDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface TourDetailsService {
    Iterable<TourDetailsResponse> getAllTourDetails(boolean sortByMarks);

    Iterable<TourDetailsResponse> getMyAllTourDetails();

    Iterable<TourDetailsResponse> findAllSortByAverageMark(PageRequest pageRequest);

    TourDetailsResponse createTourDetails(TourDetailsRequest tourDetailsDTO);

    TourDetailsResponse patchTourDetails(TourDetailsRequest newTourDetails, Long id);

    void deleteTourDetails(Long id);

    TourDetails getTourDetailsById(Long id);

    boolean existsById(Long id);

    TourDetailsResponse uploadDetailsImage(Long detailsId, MultipartFile file);

    void deleteDetailsImage(Long detailsId, String url);

    TourDetailsResponse findTourById(Long id, Boolean hotTours);

    TourDetailsResponse addHotel(Long id, Long hotelId);

    TourDetailsResponse deleteHotel(Long id, Long hotelId);

}
