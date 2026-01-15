package com.example.ticketPlatform.mappers;

import com.example.ticketPlatform.domain.CreateEventRequest;
import com.example.ticketPlatform.domain.CreateTicketTypeRequest;
import com.example.ticketPlatform.domain.Dtos.*;
import com.example.ticketPlatform.domain.UpdateEventRequest;
import com.example.ticketPlatform.domain.UpdateTicketTypeRequest;
import com.example.ticketPlatform.domain.entities.Event;
import com.example.ticketPlatform.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);
    TicketType toTicketTypeEntity(CreateTicketTypeRequest request);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    Event toEventEntity(CreateEventRequest request);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventDetailsTicketTypesResponseDto toGetEventDetailsTicketTypesResponseDto(
            TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);
    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);
    GetPublishedEventDetailsTicketTypesResponseDto toGetPublishedEventDetailsTicketTypesResponseDto(
            TicketType ticketType);

    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);


}
