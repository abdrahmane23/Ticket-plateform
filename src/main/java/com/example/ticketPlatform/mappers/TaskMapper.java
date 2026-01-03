package com.example.ticketPlatform.mappers;

import com.example.ticketPlatform.domain.Dtos.TaskForOrganizerResponseDto;
import com.example.ticketPlatform.domain.Dtos.TaskForStafferResponseDto;
import com.example.ticketPlatform.domain.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface TaskMapper {
    @Mapping(target = "stafferName", source = "task.staffer.name")
    @Mapping(target = "assignedAt", source = "task.createdAt")
    TaskForOrganizerResponseDto toTaskForOrganizerResponseDto(Task task);

    @Mapping(target = "ticketTypeName", source = "task.ticketType.name")
    @Mapping(target = "assignedAt", source = "task.createdAt")
    TaskForStafferResponseDto toTaskForStafferResponseDto(Task task);
}

