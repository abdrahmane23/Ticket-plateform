package com.example.ticketPlatform.mappers;

import com.example.ticketPlatform.domain.CreateEventRequest;
import com.example.ticketPlatform.domain.CreateTicketTypeRequest;
import com.example.ticketPlatform.domain.Dtos.CreateEventRequestDto;
import com.example.ticketPlatform.domain.Dtos.CreateEventResponseDto;
import com.example.ticketPlatform.domain.Dtos.CreateTicketTypeRequestDto;
import com.example.ticketPlatform.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

}
