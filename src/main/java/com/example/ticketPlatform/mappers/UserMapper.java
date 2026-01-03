package com.example.ticketPlatform.mappers;

import com.example.ticketPlatform.domain.Dtos.*;
import com.example.ticketPlatform.domain.entities.Task;
import com.example.ticketPlatform.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    BrowseOrganizerResponseDto toBrowseOrganizerResponseDto(User user);
    BrowseStafferResponseDto toBrowseStafferResponseDto(User user);
    GetActiveStafferResponseDto toGetActiveStafferResponseDto(User user);
    @Mapping(target = "name", source = "task.ticketType.name")
    @Mapping(target = "eventName", source = "task.ticketType.event.name")
    @Mapping(
            target = "totalAttendence",
            expression = """
    java(
      task.getTicketType().getTickets() == null ? 0
        : task.getTicketType().getTickets().size()
    )
  """
    )
    GetTaskResponseDto toGetTaskResponseDto(Task task);
    GetActiveOrganizerResponseDto toGetActiveOrganizerResponseDto(User user);
}
