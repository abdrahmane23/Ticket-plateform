package com.example.ticketPlatform.services.impl;


import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Validators.TicketValidator;
import com.example.ticketPlatform.Validators.UserValidator;
import com.example.ticketPlatform.domain.entities.Ticket;
import com.example.ticketPlatform.domain.entities.TicketStatusEnum;
import com.example.ticketPlatform.domain.entities.TicketType;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.services.NotificationDispatcher;
import com.example.ticketPlatform.services.TicketService;
import com.example.ticketPlatform.services.WaitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepo ticketRepository;
  private final UserRepo userRepository;
  private final WaitCardService waitCardService;
  private final TicketTypeRepo ticketTypeRepository;
  private final NotificationDispatcher notificationDispatcher;
  private final UserValidator userValidator;
  private final TicketValidator ticketValidator;

  @Override
  public Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable) {
    return ticketRepository.findByPurchaserId(userId, pageable);
  }

  @Override
  public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {
    return ticketRepository.findByIdAndPurchaserId(ticketId, userId);
  }

  @Override
  public void cancelTicket(UUID userId, UUID ticketId) {

    Ticket ticket = ticketValidator.getTicketIfOwnedByUser(userId, ticketId);
    User user = userValidator.getUserIfExists(userId,"USER");

    ticket.setStatus(TicketStatusEnum.CANCELLED);
    TicketType ticketType = ticket.getTicketType();
    ticketType.setTotalAvailable(ticketType.getTotalAvailable() + 1); //HANDLE CONCURRENCY!!!!
    ticketRepository.save(ticket);
    ticketTypeRepository.save(ticketType);

    UUID nextUserId= waitCardService.assignTicketToNextUser(ticketType.getId());

    if (nextUserId != null) {
      notificationDispatcher.dispatch(nextUserId, "Good news! A ticket for " + ticketType.getName() + " is now purchased for you.");
    }

  }


}
