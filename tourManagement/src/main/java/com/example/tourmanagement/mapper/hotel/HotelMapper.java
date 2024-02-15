package com.example.tourmanagement.mapper.hotel;

import com.example.tourmanagement.dto.HotelDTO;
import com.example.tourmanagement.mapper.base.DtoMapper;
import com.example.tourmanagement.mapper.tour.addons.SpecialitiesMapper;
import com.example.tourmanagement.model.entity.Hotel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {HotelReviewMapper.class, SpecialitiesMapper.class})
public interface HotelMapper extends DtoMapper<Hotel, HotelDTO> {

    @Mapping(target = "imageURL", source = "images")
    HotelDTO toDTO(Hotel entity);

    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "images", source = "imageURL")
    Hotel toEntity(HotelDTO dto);

    Iterable<HotelDTO> toDTO(Iterable<Hotel> hotels);

    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "images", source = "imageURL")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Hotel hotel, HotelDTO newHotel);
}
