package com.example.ticketPlatform.services;


import com.example.ticketPlatform.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
  TicketValidation validateTicketByQrCode(String qrCodeValue,UUID EventId,UUID TicketTypeId);
  TicketValidation validateTicketManually(UUID ticketId,UUID EventId,UUID TicketTypeId);
}
