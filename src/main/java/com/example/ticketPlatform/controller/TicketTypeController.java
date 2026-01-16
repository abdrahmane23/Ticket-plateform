package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.parseUserId;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER_ROLE')")
@RequestMapping(path = "/events/{eventId}/ticket-types")
public class TicketTypeController {

  private final TicketTypeService ticketTypeService;

  @PostMapping(path = "/{ticketTypeId}/tickets")
  public ResponseEntity<Void> purchaseTicket(
          @AuthenticationPrincipal Jwt jwt,
          @PathVariable UUID ticketTypeId,
          @RequestParam LocalDateTime timeOfpurchase
          ) {
    ticketTypeService.purchaseTicket(parseUserId(jwt), ticketTypeId, timeOfpurchase);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
