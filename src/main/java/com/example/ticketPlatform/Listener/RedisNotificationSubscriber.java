package com.example.ticketPlatform.Listener;

import com.example.ticketPlatform.domain.Dtos.NotificationDto;
import com.example.ticketPlatform.domain.Dtos.NotificationRedisDto;
import com.example.ticketPlatform.services.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisNotificationSubscriber {

    private final NotificationService notificationService; // Your EXISTING service
    private final ObjectMapper objectMapper; // For converting JSON back to objects


    public void handleMessage(String message) {
        try {
            NotificationRedisDto payload = objectMapper.readValue(message, NotificationRedisDto.class);

            NotificationDto notification = new NotificationDto(payload.getMessage(),payload.getTimestamp());

            notificationService.sendNotification(payload.getUserId(), notification);

        } catch (Exception e) {
            e.printStackTrace(); // Handle parsing errors
        }
    }
}