package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Validators.TicketTypeValidator;
import com.example.ticketPlatform.Validators.TicketValidator;
import com.example.ticketPlatform.Validators.UserValidator;
import com.example.ticketPlatform.domain.entities.Ticket;
import com.example.ticketPlatform.domain.entities.TicketStatusEnum;
import com.example.ticketPlatform.domain.entities.TicketType;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.TicketAlreadyExistException;
import com.example.ticketPlatform.exceptions.TicketTypeNotFoundException;
import com.example.ticketPlatform.exceptions.TicketsSoldOutException;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import com.example.ticketPlatform.services.QrCodeService;
import com.example.ticketPlatform.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

  private final UserRepo userRepository;
  private final TicketTypeRepo ticketTypeRepository;
  private final TicketRepo ticketRepository;
  private final QrCodeService qrCodeService;
  private  final UserValidator userValidator;
  private final TicketValidator ticketValidator;
  private final TicketTypeValidator ticketTypeValidator;

  @Override
  @Transactional
  public Ticket purchaseTicket(UUID userId, UUID ticketTypeId, LocalDateTime timeOfPurchase) {
    User user = userValidator.getUserIfExists(userId,"USER");

    TicketType ticketType = ticketTypeValidator.getTicketTypeWithLock(ticketTypeId);
    ticketTypeValidator.checkTimeOfSalesValidity(ticketType, timeOfPurchase);

    ticketValidator.checkDuplicateTicket(userId, ticketTypeId);



    Ticket ticket = new Ticket();
    ticket.setStatus(TicketStatusEnum.PURCHASED);
    ticket.setTicketType(ticketType);
    ticket.setPurchaser(user);
    ticketType.setTotalAvailable(ticketType.getTotalAvailable()-1);
    ticketTypeRepository.save(ticketType);
    Ticket savedTicket = ticketRepository.save(ticket);

    qrCodeService.generateQrCode(savedTicket);

    return ticketRepository.save(savedTicket);
  }
}
