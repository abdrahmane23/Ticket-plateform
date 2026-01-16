package com.example.ticketPlatform.services;

import com.example.ticketPlatform.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {
  Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable);

  Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId);

    void cancelTicket(UUID userId, UUID ticketId, LocalDateTime timeOfcancellation);
}
