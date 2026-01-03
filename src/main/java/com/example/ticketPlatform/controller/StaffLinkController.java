package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.domain.Dtos.BrowseStafferResponseDto;
import com.example.ticketPlatform.domain.Dtos.GetActiveOrganizerResponseDto;
import com.example.ticketPlatform.domain.Dtos.GetActiveStafferResponseDto;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.mappers.UserMapper;
import com.example.ticketPlatform.services.StafflinkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.JwtRoleExtractor.extractRoleEndingWithRole;
import static com.example.ticketPlatform.util.JwtUtil.parseUserId;
@RestController
@AllArgsConstructor
public class StaffLinkController {
    private final StafflinkService stafflinkService;
    private final UserMapper userMapper;

    @PostMapping("/staffing/{stafflinkId}")
    public ResponseEntity<Void> RemoveLink(@PathVariable UUID stafflinkId,
                                           @AuthenticationPrincipal Jwt jwt ){
        String RemoverRole = extractRoleEndingWithRole(jwt.getTokenValue());
        stafflinkService.removeLink(stafflinkId, parseUserId(jwt), RemoverRole);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/Staff")
    public ResponseEntity<Page<GetActiveStafferResponseDto>> browseStaffers(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
){
         Page <User> staffers = stafflinkService.browseStaffers(parseUserId(jwt), pageable);
        return ResponseEntity.ok(
                staffers.map(userMapper::toGetActiveStafferResponseDto)
        );
    }
    @GetMapping("Staff/{StafferId}")
    public ResponseEntity<GetActiveStafferResponseDto> browseStaffer(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID StafferId
    ){
        User staffer = stafflinkService.browseStaffer(parseUserId(jwt), StafferId);
        return ResponseEntity.ok(
                userMapper.toGetActiveStafferResponseDto(staffer)
        );
    }
    @GetMapping("/organizer")
    public ResponseEntity<GetActiveOrganizerResponseDto> browseOrganizer(
            @AuthenticationPrincipal Jwt jwt
            ){
        User staffer = stafflinkService.browseOrganizer(parseUserId(jwt));
        return ResponseEntity.ok(
                userMapper.toGetActiveOrganizerResponseDto(staffer)
        );
    }

}
