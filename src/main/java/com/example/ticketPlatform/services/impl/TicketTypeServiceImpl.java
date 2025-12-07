package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.Ticket;
import com.example.ticketPlatform.domain.entities.TicketStatusEnum;
import com.example.ticketPlatform.domain.entities.TicketType;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.TicketTypeNotFoundException;
import com.example.ticketPlatform.exceptions.TicketsSoldOutException;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import com.example.ticketPlatform.services.QrCodeService;
import com.example.ticketPlatform.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

  private final UserRepo userRepository;
  private final TicketTypeRepo ticketTypeRepository;
  private final TicketRepo ticketRepository;
  private final QrCodeService qrCodeService;

  @Override
  @Transactional
  public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
        String.format("User with ID %s was not found", userId)
    ));

    TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
        .orElseThrow(() -> new TicketTypeNotFoundException(
            String.format("Ticket type with ID %s was not found", ticketTypeId)
        ));

    int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
    Integer totalAvailable = ticketType.getTotalAvailable();

    if(purchasedTickets + 1 > totalAvailable) {
      throw new TicketsSoldOutException();
    }

    Ticket ticket = new Ticket();
    ticket.setStatus(TicketStatusEnum.PURCHASED);
    ticket.setTicketType(ticketType);
    ticket.setPurchaser(user);

    Ticket savedTicket = ticketRepository.save(ticket);
    qrCodeService.generateQrCode(savedTicket);

    return ticketRepository.save(savedTicket);
  }
}
