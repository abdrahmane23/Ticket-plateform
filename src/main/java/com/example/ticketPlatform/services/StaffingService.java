package com.example.ticketPlatform.services;

import com.example.ticketPlatform.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface StaffingService {
    public void assignStaffToEvent(UUID eventId,UUID organizerId, UUID StafferId );
    public void removeTaskFromEvent(UUID organizerId, UUID taskId );

}
