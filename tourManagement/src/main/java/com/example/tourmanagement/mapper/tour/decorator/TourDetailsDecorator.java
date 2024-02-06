package com.example.tourmanagement.mapper.tour.decorator;

import com.example.tourmanagement.dto.request.TourDetailsRequest;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.mapper.tour.TourDetailsMapper;
import com.example.tourmanagement.model.entity.TourDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class TourDetailsDecorator implements TourDetailsMapper {

    @Autowired
    @Qualifier("delegate")
    private TourDetailsMapper delegate;

    @Override
    public TourDetails toEntity(TourDetailsRequest tourDTO) {

        TourDetails details = delegate.toEntity(tourDTO);
        if (details.getPlans() != null)
            details.getPlans().forEach(p -> p.setDetails(details));

        if (details.getHotel().getId() == null)
            details.setHotel(null);

        return details;
    }

    @Override
    public TourDetailsResponse toResponse(TourDetails entity) {

        return delegate.toResponse(entity);
    }

}
