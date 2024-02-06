package com.example.tourmanagement.controller.sse;

import com.example.tourmanagement.service.sse.SseService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@CrossOrigin("*")
@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseEmitterController {

    private final SseService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        service.addEmitter(ClientContextUtils.getClient().getId(), emitter);

        return emitter;
    }
}
