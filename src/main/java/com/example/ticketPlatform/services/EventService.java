package com.example.ticketPlatform.services;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.ticketPlatform.domain.CreateEventRequest;
import com.example.ticketPlatform.domain.UpdateEventRequest;
import com.example.ticketPlatform.domain.entities.Event;
import com.example.ticketPlatform.domain.entities.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);
    Optional<Event> getEventForOrganizer(UUID organizerId, UUID id);
    Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event);
    void deleteEventForOrganizer(UUID organizerId, UUID id);
    public Page<Event> listPublishedEvents(Pageable pageable);
    Page<Event> searchPublishedEvents(String query, Pageable pageable);
    Optional<Event> getPublishedEvent(UUID id);

}
