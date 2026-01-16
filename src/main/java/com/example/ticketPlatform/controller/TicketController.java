package com.example.ticketPlatform.controller;


import com.example.ticketPlatform.domain.Dtos.GetTicketResponseDto;
import com.example.ticketPlatform.domain.Dtos.ListTicketResponseDto;
import com.example.ticketPlatform.mappers.TicketMapper;
import com.example.ticketPlatform.services.QrCodeService;
import com.example.ticketPlatform.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/tickets")
@PreAuthorize("hasAuthority('USER_ROLE')")
@RequiredArgsConstructor
public class TicketController {

  private final TicketService ticketService;
  private final TicketMapper ticketMapper;
  private final QrCodeService qrCodeService;

  @GetMapping
  public Page<ListTicketResponseDto> listTickets(
      @AuthenticationPrincipal Jwt jwt,
      Pageable pageable
  ) {
    return ticketService.listTicketsForUser(
        parseUserId(jwt),
        pageable
    ).map(ticketMapper::toListTicketResponseDto);
  }

  @GetMapping(path = "/{ticketId}")
  public ResponseEntity<GetTicketResponseDto> getTicket(
          @AuthenticationPrincipal Jwt jwt,
          @PathVariable UUID ticketId
  ) {
    return ticketService
            .getTicketForUser(parseUserId(jwt), ticketId)
            .map(ticketMapper::toGetTicketResponseDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(path = "/{ticketId}/qr-codes")
  public ResponseEntity<byte[]> getTicketQrCode(
          @AuthenticationPrincipal Jwt jwt,
          @PathVariable UUID ticketId
  ) {
    byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(
            parseUserId(jwt),
            ticketId
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);
    headers.setContentLength(qrCodeImage.length);

    return ResponseEntity.ok()
            .headers(headers)
            .body(qrCodeImage);
  }
  @PostMapping("/{ticketId}/cancel")
  public ResponseEntity<Void> cancelTicket(
          @AuthenticationPrincipal Jwt jwt,
          @PathVariable UUID ticketId,
          @RequestParam LocalDateTime timeOfCancellation
          ) {
    UUID userId = parseUserId(jwt);
    ticketService.cancelTicket(
            userId,
            ticketId,
            timeOfCancellation
    );
    return ResponseEntity.noContent().build();
  }

}
