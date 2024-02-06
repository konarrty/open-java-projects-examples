package com.example.tourmanagement.mapper;

import com.example.tourmanagement.dto.NotificationDTO;
import com.example.tourmanagement.mapper.tour.ReservationMapper;
import com.example.tourmanagement.model.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ReservationMapper.class)
public interface NotificationMapper {

    NotificationDTO toDTO(Notification entity);

    Iterable<NotificationDTO> toDTO(Iterable<Notification> plan);
}
