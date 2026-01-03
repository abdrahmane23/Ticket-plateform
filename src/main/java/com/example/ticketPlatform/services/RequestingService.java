package com.example.ticketPlatform.services;

import com.example.ticketPlatform.domain.entities.Request;
import com.example.ticketPlatform.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RequestingService {
    public void sendrequest(UUID senderId , UUID receiverId, String senderRole);

    void acceptrequest(UUID requestId, UUID uuid,String receiverRole);

    void rejectrequest(UUID requestId, UUID uuid);

    Page<User> browseOrganizers(Pageable pageable);

    Page<User> browseStaffers(Pageable pageable);

    User browseOrganizer(UUID organizerId);

    User browseStaffer(UUID stafferId);

    Page<Request> browseRequests(UUID userId,Pageable pageable);
}
