package com.example.ticketPlatform.services;

import com.example.ticketPlatform.domain.entities.Request;
import com.example.ticketPlatform.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StafflinkService {
     Page<User> browseStaffers(UUID organizerId, Pageable pageable);
     void createLink(Request request, String ReceiverRole);

    void removeLink(UUID stafflinkId, UUID UserId, String RemoverRole);

    User browseStaffer(UUID OrganizerId,UUID StafferId);

    User browseOrganizer(UUID StafferId);

}
