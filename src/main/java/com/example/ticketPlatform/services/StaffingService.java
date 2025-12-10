package com.example.ticketPlatform.services;

import com.example.ticketPlatform.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface StaffingService {
    public void AssignStaffToEvent(UUID eventId,UUID organizerId, String stafferUsername);
    public void RemoveStaffFromEvent(UUID eventId, UUID organizerId, UUID userId );


}
