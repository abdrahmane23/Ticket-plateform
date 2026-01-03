package com.example.ticketPlatform.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseEventResponseDto {
    private String name;
    private String start;
    private String end;
    private String venue;
    private String country;
    private String city;
}
