package com.example.ticketPlatform.Validators;

import com.example.ticketPlatform.Repositories.EventRepo;
import com.example.ticketPlatform.Repositories.RequestRepo;
import com.example.ticketPlatform.domain.entities.Request;
import com.example.ticketPlatform.exceptions.RequestOwnershipException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final RequestRepo requestRepo;

    public Request getRequestIfExists(UUID requestId) {
        return requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request with ID " + requestId + " not found."));
    }
    public void validateRequestOwnership(Request request, UUID userId) {
        if (!request.getReceiver().getId().equals(userId)) {
            throw new RequestOwnershipException("Request not found for this user");
        }
    }


}
