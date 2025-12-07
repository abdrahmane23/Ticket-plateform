
package com.example.ticketPlatform.services;


import com.example.ticketPlatform.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
  Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
