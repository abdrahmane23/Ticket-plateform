package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.domain.Dtos.NotificationDto;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import com.example.ticketPlatform.services.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Map<UUID, List<SseEmitter>> usersMap = new ConcurrentHashMap<>();

    @Override
    public void sendNotification(UUID userId, NotificationDto message) {
        List<SseEmitter> emitters = usersMap.get(userId);
        if (emitters != null) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(message));
                } catch (Exception e) {
                    emitters.remove(emitter);
                }
            }
        }else {
            throw new UserNotFoundException("User with ID " + userId + " not found or has no active connections.");
        }
    }

    @Override
    public SseEmitter SubscribeToNotifications(UUID userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Infinite timeout


        usersMap.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(emitter);
        emitter.onCompletion(() -> usersMap.get(userId).remove(emitter));
        emitter.onTimeout(() -> usersMap.get(userId).remove(emitter));
        return emitter;
    }
}
