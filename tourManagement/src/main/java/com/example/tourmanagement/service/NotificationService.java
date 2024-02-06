package com.example.tourmanagement.service;

import com.example.tourmanagement.dto.NotificationDTO;
import com.example.tourmanagement.model.entity.Notification;

public interface NotificationService {
    Iterable<NotificationDTO> getMyNotifications();

    NotificationDTO createNotification(Notification note);

    void deleteNotification(Long noteId);
}
