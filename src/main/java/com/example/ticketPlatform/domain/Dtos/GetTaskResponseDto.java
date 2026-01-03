package com.example.ticketPlatform.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskResponseDto {
    private String name;
    private String eventName;
    private int totalAttendence;

}
