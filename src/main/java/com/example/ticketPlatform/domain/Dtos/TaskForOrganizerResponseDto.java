package com.example.ticketPlatform.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskForOrganizerResponseDto {
    private UUID id;
    private String stafferName;
    private LocalDateTime assignedAt;
}
