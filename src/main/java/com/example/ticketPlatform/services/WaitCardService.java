package com.example.ticketPlatform.services;


import com.example.ticketPlatform.domain.entities.TicketType;

import java.util.UUID;

public interface WaitCardService {
    public void createWaitCard(UUID ticketTypeId, UUID userId);

    void assignTicketToNextUser(UUID ticketTypeId);
}
