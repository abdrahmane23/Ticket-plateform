package com.example.ticketPlatform.services;

import com.example.ticketPlatform.domain.CreateEventRequest;
import com.example.ticketPlatform.domain.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);

}
