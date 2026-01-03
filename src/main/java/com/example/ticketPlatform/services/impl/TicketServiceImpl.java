package com.example.ticketPlatform.services.impl;


import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Repositories.WaitCardRepo;
import com.example.ticketPlatform.domain.entities.Ticket;
import com.example.ticketPlatform.domain.entities.TicketStatusEnum;
import com.example.ticketPlatform.domain.entities.TicketType;
import com.example.ticketPlatform.domain.entities.User;
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
    Ticket ticket = getTicketIfOwnedByUser(userId, ticketId);
    System.out.println(userId);
    User user = userRepository.findById(userId)
            .orElseThrow(
                    () -> new RuntimeException("User not found")
            );
    ticket.setStatus(TicketStatusEnum.CANCELLED);
    TicketType ticketType = ticket.getTicketType();
    ticketType.setTotalAvailable(ticketType.getTotalAvailable() + 1);
    ticketRepository.save(ticket);
    ticketTypeRepository.save(ticketType);
    waitCardService.assignTicketToNextUser(ticketType.getId());





  }

  private Ticket getTicketIfOwnedByUser(UUID userId, UUID ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
            () -> new RuntimeException("Ticket not found")
    );
    if (!ticket.getPurchaser().getId().equals(userId)) {
      throw new RuntimeException("Ticket does not belong to user");
    }
    return ticket;
  }
}
