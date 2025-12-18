package com.example.ticketPlatform.services;

import java.util.UUID;

public interface RequestingService {
    public void sendrequest(UUID senderId , UUID receiverId, String senderRole);

    void acceptrequest(UUID requestId, UUID uuid,String receiverRole);

    void rejectrequest(UUID requestId, UUID uuid);

    void removeLink(UUID stafflinkId, UUID UserId,String RemoverRole);
}
