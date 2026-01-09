package com.example.ticketPlatform.services;

import com.example.ticketPlatform.configuration.RedisConfig;
import com.example.ticketPlatform.domain.Dtos.NotificationDto;
import com.example.ticketPlatform.domain.Dtos.NotificationRedisDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationDispatcher {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void dispatch(UUID userId, String content) {
        // Create the payload
        NotificationRedisDto payload = new NotificationRedisDto(userId, content, LocalDateTime.now());

        try {
            // Convert to JSON
            String jsonMessage = objectMapper.writeValueAsString(payload);

            // PUBLISH to the global topic
            redisTemplate.convertAndSend(RedisConfig.NOTIFICATION_TOPIC, jsonMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}