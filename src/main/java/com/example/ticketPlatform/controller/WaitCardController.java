package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.services.WaitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
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
public class WaitCardController {
    private final WaitCardService waitCardService;

    @PostMapping("/{ticketTypeId}/wait-cards" )
    public ResponseEntity<Void> createWaitCard(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId,
            @RequestParam LocalDateTime time0fRequest
            ) {
        UUID userId = parseUserId(jwt);
        waitCardService.createWaitCard (ticketTypeId,userId, time0fRequest);
        return ResponseEntity.ok().build();

    }
}
