package com.example.ticketPlatform.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseOrganizerResponseDto {
    private UUID id;
    private String name;
    private String email;

}
