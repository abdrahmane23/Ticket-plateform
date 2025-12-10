package com.example.ticketPlatform.domain.Dtos;

import com.example.ticketPlatform.domain.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventResponseDto {

  private UUID id;
  private String name;
  private LocalDateTime start;
  private LocalDateTime end;
  private String venue;
  private String country;
  private String city;
  private LocalDateTime salesStart;
  private LocalDateTime salesEnd;
  private EventStatusEnum status;
  private List<CreateTicketTypeResponseDto> ticketTypes;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
