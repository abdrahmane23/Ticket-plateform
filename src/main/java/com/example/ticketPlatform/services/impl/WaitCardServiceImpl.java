package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Repositories.WaitCardRepo;
import com.example.ticketPlatform.Validators.TicketTypeValidator;
import com.example.ticketPlatform.Validators.TicketValidator;
import com.example.ticketPlatform.Validators.UserValidator;
import com.example.ticketPlatform.domain.entities.*;
import com.example.ticketPlatform.exceptions.*;
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
    private final UserValidator userValidator;
    private final TicketTypeValidator ticketTypeValidator;
    private final TicketValidator ticketValidator;

    @Override
    public void createWaitCard(UUID ticketTypeId, UUID userId) {
        TicketType ticketType = ticketTypeValidator.getTicketTypeIfExist(ticketTypeId);
        User user = userValidator.getUserIfExists(userId,"USER");

        ticketTypeValidator.checkTicketTypeAvailability(ticketType);
        ticketValidator.checkDuplicateTicket(userId, ticketTypeId);

        WaitCard waitCard = new WaitCard();
        waitCard.setTicketType(ticketType);
        waitCard.setWaiter(user);
        try {
            waitCardRepo.save(waitCard);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateWaitcardException("Wait card already exists for this user and ticket type");
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
