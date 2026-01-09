package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Repositories.WaitCardRepo;
import com.example.ticketPlatform.domain.entities.*;
import com.example.ticketPlatform.exceptions.TicketTypeNotFoundException;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import com.example.ticketPlatform.services.TicketTypeService;
import com.example.ticketPlatform.services.WaitCardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class WaitCardServiceImpl implements WaitCardService {
    private final TicketTypeRepo ticketTypeRepo;
    private final UserRepo userRepo;
    private final WaitCardRepo waitCardRepo;
    private final TicketRepo ticketRepo;
    private final TicketTypeService ticketTypeService;

    @Override
    public void createWaitCard(UUID ticketTypeId, UUID userId) {
        TicketType ticketType = ticketTypeRepo.findById(ticketTypeId).
                orElseThrow(
                        () -> new TicketTypeNotFoundException("Ticket type not found")
                );
        User user = userRepo.findById(userId).
                orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
        if (ticketType.getTotalAvailable()>0){
            throw new RuntimeException("Tickets are available, no need to create wait card");
        }
        if( ticketRepo.existsByTicketTypeIdAndPurchaserIdAndStatus(ticketTypeId, userId, TicketStatusEnum.PURCHASED)) {
            throw new RuntimeException("User already has a ticket for this ticket type");
        }
        WaitCard waitCard = new WaitCard();
        waitCard.setTicketType(ticketType);
        waitCard.setWaiter(user);
        try {
            waitCardRepo.save(waitCard);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Wait card already exists for this user and ticket type");
        }
    }

    @Override
    @Transactional
    public UUID assignTicketToNextUser(UUID ticketTypeId) {
        User nextUser = waitCardRepo.findFirstByTicketTypeIdOrderByCreatedAtAsc(ticketTypeId).map(
                WaitCard::getWaiter
        ).orElse(null);
        if (nextUser != null) {
            waitCardRepo.deleteByTicketTypeIdAndWaiterId(ticketTypeId, nextUser.getId());
            Ticket ticket= ticketTypeService.purchaseTicket( nextUser.getId(),ticketTypeId);
        }
        return nextUser != null ? nextUser.getId() : null;

    }

}
