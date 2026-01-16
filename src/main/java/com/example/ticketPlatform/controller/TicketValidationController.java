package com.example.ticketPlatform.controller;


import com.example.ticketPlatform.domain.Dtos.TicketValidationRequestDto;
import com.example.ticketPlatform.domain.Dtos.TicketValidationResponseDto;
import com.example.ticketPlatform.domain.entities.TicketValidation;
import com.example.ticketPlatform.domain.entities.TicketValidationMethod;
import com.example.ticketPlatform.mappers.TicketValidationMapper;
import com.example.ticketPlatform.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "{EventId}/{TicketTypeId}/ticket-validations")
@PreAuthorize("hasAuthority('STAFF_ROLE')")
@RequiredArgsConstructor
public class TicketValidationController {

  private final TicketValidationService ticketValidationService;
  private final TicketValidationMapper ticketValidationMapper;

  @PostMapping
  public ResponseEntity<TicketValidationResponseDto> validateTicket(
          @RequestBody TicketValidationRequestDto ticketValidationRequestDto,
          @PathVariable UUID EventId,
            @PathVariable UUID TicketTypeId

          ){
    TicketValidationMethod method = ticketValidationRequestDto.getMethod();
    TicketValidation ticketValidation;
    if(TicketValidationMethod.MANUAL.equals(method)) {
      ticketValidation = ticketValidationService.validateTicketManually(
          ticketValidationRequestDto.getId(),EventId,TicketTypeId);
    } else {
      ticketValidation = ticketValidationService.validateTicketByQrCode(
          ticketValidationRequestDto.getValue(),EventId,TicketTypeId
      );
    }
    return ResponseEntity.ok(
        ticketValidationMapper.toTicketValidationResponseDto(ticketValidation)
    );
  }

}
