package com.example.tourmanagement.mapper;

import com.example.tourmanagement.dto.TourMarkRequest;
import com.example.tourmanagement.dto.response.TourMarkResponse;
import com.example.tourmanagement.mapper.clients.ClientMapper;
import com.example.tourmanagement.model.entity.TourMark;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface TourMarkMapper {

    TourMarkResponse toResponse(TourMark mark);

    @Mapping(target = "client", ignore = true)
    @Mapping(source = "detailsId", target = "details.id")
    TourMark toEntity(TourMarkRequest markDTO);

    Iterable<TourMarkResponse> toResponse(Iterable<TourMark> marks);

    @Mapping(target = "details", ignore = true)
    @Mapping(target = "client", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget TourMark target, TourMarkRequest source);
}
