package com.example.ticketPlatform.services.impl;


import com.example.ticketPlatform.Repositories.QrCodeRepo;
import com.example.ticketPlatform.Repositories.TicketRepo;
import com.example.ticketPlatform.Repositories.TicketValidationRepo;
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

  @Override
  public TicketValidation validateTicketByQrCode(String QrValue,UUID EventId,UUID TicketTypeId) {
    QrCode qrCode = qrCodeRepository.findByValue(QrValue)
        .orElseThrow(() -> new QrCodeNotFoundException(
            String.format(
                "QR Code with value %s was not found",QrValue
            )
        ));

    Ticket ticket = qrCode.getTicket();
    CheckMatch(ticket,EventId,TicketTypeId);
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
    Ticket ticket = ticketRepository.findById(TicketId)
        .orElseThrow(TicketNotFoundException::new);
    CheckMatch(ticket,EventId,TicketTypeId);
    return validateTicket(ticket, TicketValidationMethod.MANUAL);
  }
  private void CheckMatch(Ticket ticket,UUID EventId,UUID TicketTypeId){
    System.out.println(ticket.getTicketType().getEvent().getId());
    System.out.println(EventId);
    if(!ticket.getTicketType().getEvent().getId().equals(EventId)){
      throw new TicketEventMismatchException("Ticket doesnt belong to this event");
    }
    if (!ticket.getTicketType().getId().equals(TicketTypeId)){
      throw new TicketTypeMismatchException("Ticket doesnt belong to this section");
    }
  }
}
