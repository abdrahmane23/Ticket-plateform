package com.example.ticketPlatform.Validators;

import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.domain.entities.TicketType;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class TicketTypeValidator {
    private final TicketTypeRepo ticketTypeRepo;
    private final UserValidator userValidator;
    private final TicketRepo ticketRepo;


    public TicketType getTicketTypeIfValid(UUID ticketTypeId, UUID organizerId) {
        TicketType AssignedTicketType = getTicketTypeIfExist(ticketTypeId);
        User organizer=  userValidator.getUserIfExists(organizerId,"ORGANIZER");

        if (!AssignedTicketType.getEvent().getOrganizer().equals(organizer)) {
            throw new EventOwnershipException("Organizer with ID "+organizerId+" is not the organizer of event with ID "+AssignedTicketType.getEvent().getOrganizer().getId());
        }
        return AssignedTicketType;
    }

    public TicketType getTicketTypeIfExist(UUID ticketTypeId) {
        return ticketTypeRepo.findById(ticketTypeId).orElseThrow(
                ()-> new TicketTypeNotFoundException("Event with ID " + ticketTypeId + " not found"));
    }

    public void verifyTicketTypeOwnership(UUID ticketTypeId, UUID organizerId) {
        TicketType AssignedTicketType = getTicketTypeIfExist(ticketTypeId);
        User organizer=  userValidator.getUserIfExists(organizerId,"ORGANIZER");
        if (!AssignedTicketType.getEvent().getOrganizer().equals(organizer)) {
            throw new EventOwnershipException("Organizer with ID "+organizerId+" is not the organizer of event with ID "+AssignedTicketType.getEvent().getOrganizer().getId());
        }
    }

    public TicketType getTicketTypeWithLock(UUID ticketTypeId) {
        return ticketTypeRepo.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(
                        String.format("Ticket type with ID %s was not found", ticketTypeId)
                ));
    }
    public void checkTicketAvailability(TicketType ticketType) {
        int purchasedTickets = ticketRepo.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }
    }
    public void checkTicketTypeAvailability(TicketType ticketType) {
        if (ticketType.getTotalAvailable()>0){
            throw new TicketAvailableException("Tickets are available, no need to create wait card");
        }
    }
    public void checkTimeOfSalesValidity(TicketType ticketType, LocalDateTime timeOfPurchase) {
        if (ticketType.getEvent().getSalesEnd().isBefore(timeOfPurchase) ||
                ticketType.getEvent().getSalesStart().isAfter(timeOfPurchase)) {
            throw new InvalidTimeOfSalesException("Sales end time cannot be before sales start time");
        }
    }
}
