package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.domain.Dtos.BrowseEventResponseDto;
import com.example.ticketPlatform.domain.Dtos.BrowseOrganizerResponseDto;
import com.example.ticketPlatform.domain.Dtos.BrowseRequestResponseDto;
import com.example.ticketPlatform.domain.Dtos.BrowseStafferResponseDto;
import com.example.ticketPlatform.domain.entities.Request;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.mappers.RequestMapper;
import com.example.ticketPlatform.mappers.UserMapper;
import com.example.ticketPlatform.services.RequestingService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.JwtRoleExtractor.extractRoleEndingWithRole;
import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/requests")
@PreAuthorize("hasAnyAuthority('ORGANIZER_ROLE','STAFF_ROLE')")
@RequiredArgsConstructor
public class RequestController {
    private final RequestingService requestingService;
    private final UserMapper userMapper;
    private final RequestMapper requestMapper;


    @PostMapping("")
    public ResponseEntity<Void> SendRequest(@AuthenticationPrincipal Jwt jwt,
                                            @RequestBody @NotNull UUID receiverId){

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
    @GetMapping("/organizers")
    public ResponseEntity<Page<BrowseOrganizerResponseDto>> browseOrganizers(Pageable pageable) {
        Page<User> organizers = requestingService.browseOrganizers(pageable);
        return ResponseEntity.ok(
                organizers.map(userMapper::toBrowseOrganizerResponseDto));
    }

    @GetMapping("/staffers")
    public ResponseEntity<Page<BrowseStafferResponseDto>> browseStaffers(Pageable pageable) {
        Page<User> staffers = requestingService.browseStaffers(pageable);
        return ResponseEntity.ok(
                staffers.map(userMapper::toBrowseStafferResponseDto));
    }
    @GetMapping("organizers/{organizerId}")
    public ResponseEntity<BrowseOrganizerResponseDto> browseOrganizer(UUID organizerId) {
        User organizer = requestingService.browseOrganizer(organizerId);
        BrowseOrganizerResponseDto organizerResponseDto = userMapper.toBrowseOrganizerResponseDto(organizer);
        return ResponseEntity.ok(organizerResponseDto);
    }
    @GetMapping("staffers/{stafferId}")
    public ResponseEntity<BrowseStafferResponseDto> browseStaffer(UUID stafferId) {
        User organizer = requestingService.browseStaffer(stafferId);
        BrowseStafferResponseDto stafferResponseDto = userMapper.toBrowseStafferResponseDto(organizer);
        return ResponseEntity.ok(stafferResponseDto);
    }
    @GetMapping()
    public ResponseEntity<Page<BrowseRequestResponseDto>> browseRequests(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ) {
        UUID userId = parseUserId(jwt);
        Page<Request> requests = requestingService.browseRequests(userId,pageable);
        return ResponseEntity.ok(
                requests.map(requestMapper::toBrowseRequestResponseDto)
        );
    }
    //IMPLEMENTING SENT REQUESTS WHEN WE REFACTOR REQUEST TO BE CANCELED.





}

