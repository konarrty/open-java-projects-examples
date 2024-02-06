package com.example.tourmanagement.mapper.hotel;

import com.example.tourmanagement.dto.HotelReviewDTO;
import com.example.tourmanagement.mapper.base.DtoMapper;
import com.example.tourmanagement.model.entity.Hotel;
import com.example.tourmanagement.model.entity.HotelReview;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HotelReviewMapper extends DtoMapper<HotelReview, HotelReviewDTO> {

    @Mapping(target = "hotelId", source = "hotel.id")
    HotelReviewDTO toDTO(HotelReview entity);

    @Mapping(source = "hotelId", target = "hotel", qualifiedByName = "hotelMapping")
    HotelReview toEntity(HotelReviewDTO dto);

    Iterable<HotelReviewDTO> toDTO(Iterable<HotelReview> reviews);

    @Mapping(source = "hotelId", target = "hotel", qualifiedByName = "hotelMapping")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget HotelReview target, HotelReviewDTO dto);

    @Named("hotelMapping")
    default Hotel hotelMapping(Long hotelId) {

        if (hotelId == null)
            return null;

        return new Hotel(hotelId);
    }


}
