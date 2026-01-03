package com.example.ticketPlatform.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderResponseDto {
    private UUID id;
    private String name;
    private String email;
}
