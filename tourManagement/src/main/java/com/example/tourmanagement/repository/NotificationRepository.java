package com.example.tourmanagement.repository;

import com.example.tourmanagement.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Iterable<Notification> findAllByReservationClientId(Long clientId);
}
