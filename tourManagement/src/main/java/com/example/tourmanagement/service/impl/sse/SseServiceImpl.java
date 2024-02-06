package com.example.tourmanagement.service.impl.sse;

import com.example.tourmanagement.dto.NotificationDTO;
import com.example.tourmanagement.service.sse.SseService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseServiceImpl implements SseService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public void addEmitter(Long clientId, SseEmitter emitter) {
        emitters.put(clientId, emitter);

        emitter.onCompletion(() -> emitters.remove(clientId));
        emitter.onTimeout(() -> emitters.remove(clientId));
    }

    @Override
    public void sendEvent(Long clientId, NotificationDTO note) {

        SseEmitter emitter = emitters.get(clientId);
        if (emitter == null)
            return;

        try {
            emitter.send(note);

        } catch (IOException e) {
            emitter.complete();
            emitters.remove(clientId);
        }
    }


}