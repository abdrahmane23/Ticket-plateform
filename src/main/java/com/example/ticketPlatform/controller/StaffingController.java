package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.domain.entities.Event;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.services.StaffingService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "{eventId}/{ticketTypeId}/staffings")
@RequiredArgsConstructor
public class StaffingController {
    private final StaffingService staffingService ;


    @PostMapping
    public ResponseEntity<Void> AssignStaffer(@PathVariable UUID ticketTypeId,
                                              @RequestParam String stafferUsername,
                                              @AuthenticationPrincipal Jwt jwt) {
        staffingService.AssignStaffToEvent(ticketTypeId, parseUserId(jwt) , stafferUsername );
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> RemoveStaffer(@PathVariable UUID ticketTypeId,
                                              @RequestParam UUID userId,
                                              @AuthenticationPrincipal Jwt jwt) {
        staffingService.RemoveStaffFromEvent(ticketTypeId, parseUserId(jwt) ,userId );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
