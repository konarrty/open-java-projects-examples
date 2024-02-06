package com.example.tourmanagement.controller;

import com.example.tourmanagement.dto.NotificationDTO;
import com.example.tourmanagement.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my")
    public ResponseEntity<?> getMyNotifications() {

        Iterable<NotificationDTO> notifications = notificationService.getMyNotifications();
        if (notifications.iterator().hasNext())
            return ResponseEntity.ok(notifications);
        else
            return ResponseEntity.notFound().build();
    }

}
