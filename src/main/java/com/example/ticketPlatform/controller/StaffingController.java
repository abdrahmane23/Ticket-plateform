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

import java.util.List;
import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "{eventId}/{ticketTypeId}/staffings")
@RequiredArgsConstructor
public class StaffingController {
    private final StaffingService staffingService ;

    @PostMapping
    public ResponseEntity<Void> AssignStaffer(@PathVariable UUID ticketTypeId,
                                              @RequestParam UUID stafferId,
                                              @AuthenticationPrincipal Jwt jwt) {
        staffingService.assignStaffToEvent(ticketTypeId, parseUserId(jwt) , stafferId );
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "{taskId}")
    public ResponseEntity<Void> RemoveStaffer(@PathVariable UUID taskId,
                                              @AuthenticationPrincipal Jwt jwt) {
        staffingService.removeTaskFromEvent( parseUserId(jwt),taskId );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
