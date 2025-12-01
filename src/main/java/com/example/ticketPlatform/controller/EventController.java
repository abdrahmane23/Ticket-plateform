package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.domain.CreateEventRequest;
import com.example.ticketPlatform.domain.Dtos.CreateEventRequestDto;
import com.example.ticketPlatform.domain.Dtos.CreateEventResponseDto;
import com.example.ticketPlatform.domain.entities.Event;
import com.example.ticketPlatform.mappers.EventMapper;
import com.example.ticketPlatform.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path="/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto>createEvent(
            @AuthenticationPrincipal Jwt jwt
            , @Valid @RequestBody CreateEventRequestDto createEventRequestDto
    ){
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID organizerId =UUID.fromString(  jwt.getSubject());
        Event createdEvent = eventService.createEvent(organizerId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);



    }

}
