package com.example.ticketPlatform.mappers;

import com.example.ticketPlatform.domain.Dtos.BrowseRequestResponseDto;
import com.example.ticketPlatform.domain.Dtos.SenderResponseDto;
import com.example.ticketPlatform.domain.entities.Request;
import com.example.ticketPlatform.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {
    BrowseRequestResponseDto toBrowseRequestResponseDto(Request request);
    SenderResponseDto toSenderResponseDto(User sender);
}
