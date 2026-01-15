package com.example.ticketPlatform.services.impl;


import com.example.ticketPlatform.Repositories.QrCodeRepo;
import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketValidationRepo;
import com.example.ticketPlatform.Validators.QrCodeValidator;
import com.example.ticketPlatform.Validators.TicketValidator;
import com.example.ticketPlatform.domain.entities.QrCode;
import com.example.ticketPlatform.domain.entities.Ticket;
import com.example.ticketPlatform.domain.entities.TicketValidation;
import com.example.ticketPlatform.domain.entities.TicketValidationMethod;
import com.example.ticketPlatform.exceptions.*;
import com.example.ticketPlatform.services.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

  private final QrCodeRepo qrCodeRepository;
  private final TicketValidationRepo ticketValidationRepository;
  private final TicketRepo ticketRepository;
  private final QrCodeValidator qrCodeValidator;
  private final TicketValidator ticketValidator;

  @Override
  public TicketValidation validateTicketByQrCode(String QrValue,UUID EventId,UUID TicketTypeId) {
    QrCode qrCode = qrCodeValidator.getQrCodeIfExists(QrValue);

    Ticket ticket = qrCode.getTicket();
    ticketValidator.CheckMatch(ticket,EventId,TicketTypeId);
    return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
  }

  private TicketValidation validateTicket(Ticket ticket,
      TicketValidationMethod ticketValidationMethod) {

    TicketValidation ticketValidation = new TicketValidation();
    ticketValidation.setTicket(ticket);
    ticketValidation.setValidationMethod(ticketValidationMethod);
    try {
        TicketValidation v = ticketValidationRepository.save(ticketValidation);
        ticketValidationRepository.flush();
        return v;
    }catch ( DataIntegrityViolationException ex ){
        throw new TicketAlreadyValidatedException("Ticket has already been validated");
    }
  }

  @Override
  public TicketValidation validateTicketManually(UUID TicketId,UUID EventId,UUID TicketTypeId) {
    Ticket ticket = ticketValidator.getTicketIfExists(TicketId);
    ticketValidator.CheckMatch(ticket,EventId,TicketTypeId);
    return validateTicket(ticket, TicketValidationMethod.MANUAL);
  }

}
