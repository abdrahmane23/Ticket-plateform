package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.EventRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.CreateEventRequest;
import com.example.ticketPlatform.domain.entities.Event;
import com.example.ticketPlatform.domain.entities.TicketType;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import com.example.ticketPlatform.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepo useRepository;
    private final EventRepo eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer= useRepository.findById(organizerId)
                .orElseThrow(
                        () -> new UserNotFoundException("User with id '%s' not found")
                        );
        List<TicketType> newTicketType = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypetoCreate = new TicketType();
                    ticketTypetoCreate.setName(ticketType.getName());
                    ticketTypetoCreate.setPrice(ticketType.getPrice());
                    ticketTypetoCreate.setDescription(ticketType.getDescription());
                    ticketTypetoCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    return ticketTypetoCreate;
                }).toList();
        Event newEvent = new Event();
        newEvent.setName(event.getName());
        newEvent.setStart(event.getStart());
        newEvent.setEnd(event.getEnd());
        newEvent.setVenue(event.getVenue());
        newEvent.setSalesStart(event.getSalesStart());
        newEvent.setSalesEnd(event.getSalesEnd());
        newEvent.setStatus(event.getStatus());
        newEvent.setOrganizer(organizer);
        newEvent.setTicketTypes(newTicketType);

        return eventRepository.save(newEvent);
    }
}
