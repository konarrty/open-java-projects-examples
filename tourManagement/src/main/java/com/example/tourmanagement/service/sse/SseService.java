package com.example.tourmanagement.service.sse;

import com.example.tourmanagement.dto.NotificationDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    void addEmitter(Long clientId, SseEmitter emitter);

    void sendEvent(Long clientId, NotificationDTO note);
}
