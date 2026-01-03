package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.services.WaitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequiredArgsConstructor
public class WaitCardController {
    private final WaitCardService waitCardService;

    @PostMapping("/wait-cards/{ticketTypeId}" )
    public ResponseEntity<Void> createWaitCard(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId
            ) {
        UUID userId = parseUserId(jwt);
        waitCardService.createWaitCard (ticketTypeId,userId);
        return ResponseEntity.ok().build();

    }
}
