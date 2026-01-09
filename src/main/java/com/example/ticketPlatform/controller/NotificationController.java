package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/notifications/subscribe")
    public SseEmitter subscribeToNotifications(@AuthenticationPrincipal Jwt jwt) {
        UUID userId  = parseUserId(jwt);
         SseEmitter emitter= notificationService.SubscribeToNotifications(userId);
            return emitter;
    }
}
