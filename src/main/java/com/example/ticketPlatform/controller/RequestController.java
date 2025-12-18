package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.services.RequestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.JwtRoleExtractor.extractRoleEndingWithRole;
import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/requesting")
@RequiredArgsConstructor
public class RequestController {
    private final RequestingService requestingService;


    @PostMapping("/{receiverId}")
    public ResponseEntity<Void> SendRequest(@PathVariable UUID  receiverId,
                                               @AuthenticationPrincipal Jwt jwt ){

        String senderRole = extractRoleEndingWithRole(jwt.getTokenValue());
        requestingService.sendrequest( parseUserId(jwt),receiverId, senderRole);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{requestId}/accept")
    public ResponseEntity<Void> AcceptRequest(@PathVariable UUID  requestId,
                                              @AuthenticationPrincipal Jwt jwt
                                              ){
        String ReceiverRole = extractRoleEndingWithRole(jwt.getTokenValue());
        System.out.println(ReceiverRole);
        requestingService.acceptrequest( requestId, parseUserId(jwt), ReceiverRole);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{requestId}/reject")
    public ResponseEntity<Void> RejectRequest(@PathVariable UUID  requestId,
                                              @AuthenticationPrincipal Jwt jwt
    ){

        requestingService.rejectrequest( requestId, parseUserId(jwt));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/staffing/{stafflinkId}")
    public ResponseEntity<Void> RemoveLink(@PathVariable UUID  stafflinkId,
                                               @AuthenticationPrincipal Jwt jwt ){
        String RemoverRole = extractRoleEndingWithRole(jwt.getTokenValue());
        requestingService.removeLink(stafflinkId, parseUserId(jwt), RemoverRole);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

