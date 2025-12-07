package com.example.ticketPlatform.domain.Dtos;

import com.example.ticketPlatform.domain.entities.TicketValidationMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDto {
  private UUID id;
  private String value;
  private TicketValidationMethod method;
}
