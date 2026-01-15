package com.example.ticketPlatform.Validators;

import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.domain.entities.Ticket;
import com.example.ticketPlatform.domain.entities.TicketStatusEnum;
import com.example.ticketPlatform.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TicketValidator {
    private  final TicketRepo ticketRepository;
    public Ticket getTicketIfOwnedByUser(UUID userId, UUID ticketId) {
        Ticket ticket = getTicketIfExists(ticketId);
        if (!ticket.getPurchaser().getId().equals(userId)) {
            throw new TicketOwnershipException("Ticket does not belong to user");
        }
        return ticket;
    }
    public Ticket getTicketIfExists(UUID ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(
                () -> new TicketNotFoundException("Ticket not found")
        );
    }
    public void checkDuplicateTicket(UUID userId, UUID ticketTypeId) {
        if (ticketRepository.existsByTicketTypeIdAndPurchaserIdAndStatus(ticketTypeId, userId, TicketStatusEnum.PURCHASED)) {
            throw new TicketAlreadyExistException("User already has a ticket for this ticket type");
        }
    }
    public void CheckMatch(Ticket ticket,UUID EventId,UUID TicketTypeId){
        if(!ticket.getTicketType().getEvent().getId().equals(EventId)){
            throw new TicketEventMismatchException("Ticket doesnt belong to this event");
        }
        if (!ticket.getTicketType().getId().equals(TicketTypeId)){
            throw new TicketTypeMismatchException("Ticket doesnt belong to this section");
        }
    }
}
