package com.example.ticketPlatform.services;


import com.example.ticketPlatform.domain.entities.TicketType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface WaitCardService {
    public void createWaitCard(UUID ticketTypeId, UUID userId,LocalDateTime timeOfRequest);

    UUID assignTicketToNextUser(UUID ticketTypeId, LocalDateTime timeOfPurchase);
}
