package com.example.ticketPlatform.domain.Dtos;

import com.example.ticketPlatform.domain.entities.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {

  @NotBlank(message = "Event name is required")
  private String name;

  private LocalDateTime start;

  private LocalDateTime end;

  @NotBlank(message = "Venue information is required")
  private String venue;

  @NotBlank(message = "country information is required")
  private String country;

  @NotBlank(message = "city information is required")
  private String city;


  private LocalDateTime salesStart;

  private LocalDateTime salesEnd;

  @NotNull(message = "Event status must be provided")
  private EventStatusEnum status;

  @NotEmpty(message = "At least one ticket type is required")
  @Valid
  private List<CreateTicketTypeRequestDto> ticketTypes;

}
