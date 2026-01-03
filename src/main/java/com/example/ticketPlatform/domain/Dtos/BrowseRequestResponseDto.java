package com.example.ticketPlatform.domain.Dtos;

import com.example.ticketPlatform.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseRequestResponseDto {
    private UUID id ;
    private SenderResponseDto sender;
    private LocalDateTime createdAt;
}
