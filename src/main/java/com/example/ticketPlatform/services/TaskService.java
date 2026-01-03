package com.example.ticketPlatform.services;

import com.example.ticketPlatform.domain.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TaskService {
    public void assignStaffToEvent(UUID eventId,UUID organizerId, UUID StafferId );
    public void removeTaskFromEvent(UUID organizerId, UUID taskId );
    Page<Task> browseTasksForOrganizer(UUID ticketTypeId, UUID uuid, Pageable pageable);
    Task browseTaskForStaffer(UUID StafferId, UUID uuid);
}
