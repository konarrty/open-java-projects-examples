package com.example.tourmanagement.service.impl;

import com.example.tourmanagement.dto.NotificationDTO;
import com.example.tourmanagement.mapper.NotificationMapper;
import com.example.tourmanagement.model.entity.Notification;
import com.example.tourmanagement.repository.NotificationRepository;
import com.example.tourmanagement.service.NotificationService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    @Override
    public Iterable<NotificationDTO> getMyNotifications() {
        Iterable<Notification> notes = notificationRepository.findAllByReservationClientId(
                ClientContextUtils.getClient().getId()
        );

        return notificationMapper.toDTO(notes);
    }

    @Override
    public NotificationDTO createNotification(Notification note) {
        notificationRepository.save(note);

        return notificationMapper.toDTO(note);
    }

    @Override
    public void deleteNotification(Long noteId) {

        notificationRepository.deleteById(noteId);
    }

}
