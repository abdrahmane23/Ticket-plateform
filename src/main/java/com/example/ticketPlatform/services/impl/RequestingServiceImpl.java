package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.RequestRepo;
import com.example.ticketPlatform.Repositories.StaffLinkRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Validators.RequestValidator;
import com.example.ticketPlatform.Validators.UserValidator;
import com.example.ticketPlatform.domain.entities.*;
import com.example.ticketPlatform.exceptions.*;
import com.example.ticketPlatform.services.NotificationDispatcher;
import com.example.ticketPlatform.services.RequestingService;
import com.example.ticketPlatform.services.StafflinkService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RequestingServiceImpl implements RequestingService {
    private final UserRepo userRepo;
    private final RequestRepo requestRepo;
    private final StaffLinkRepo staffLinkRepo;
    private final NotificationDispatcher notificationDispatcher;
    private final StafflinkService stafflinkService;
    private final UserValidator userValidator;
    private final RequestValidator requestValidator;
    @Transactional
    @Override
    public void sendrequest(UUID senderId, UUID receiverId, String senderRole) {
        User sender = userValidator.getUserIfExists(senderId,"SENDER");
        User receiver = userValidator.getUserIfExists(receiverId,"RECEIVER");

        boolean ActiveLinkExist =
                staffLinkRepo.existsByStafferIdAndOrganizerIdAndStatus(receiverId, senderId, LinkStatusEnum.ACTIVE ) ||
                staffLinkRepo.existsByStafferIdAndOrganizerIdAndStatus(senderId, receiverId, LinkStatusEnum.ACTIVE );

        if (ActiveLinkExist) {
            throw new DuplicateRequestException("Staff link already exists between these users");
        }
        if (senderRole.equals("STAFF_ROLE") && staffLinkRepo.existsByStafferIdAndStatus(senderId, LinkStatusEnum.ACTIVE)) {
            throw new   RequestException("Staffer with ID " + senderId + " is already on link, cannot send request");
        }

        Request request = new Request();
        request.setSender(sender);
        request.setReceiver(receiver);
        try {
            requestRepo.save(request);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRequestException("Request already sent");
        }
    }

    @Transactional
    @Override
    public void acceptrequest(UUID requestId, UUID uuid, String receiverRole) {
        Request request = requestValidator.getRequestIfExists(requestId);

        requestValidator.validateRequestOwnership(request, uuid);
        stafflinkService.createLink(request, receiverRole);
        requestRepo.delete(request);
    }

    @Override
    public void rejectrequest(UUID requestId, UUID uuid) {
        Request request = requestRepo.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException("Request not found")
        );
        requestValidator.validateRequestOwnership(request, uuid);
        requestRepo.delete(request);
    }
    @Override
    public Page<User> browseOrganizers(Pageable pageable) {
        return userRepo.findByRole("ORGANIZER_ROLE",pageable);
    }

    @Override
    public Page<User> browseStaffers(Pageable pageable) {
        return userRepo.findByRole("STAFF_ROLE",pageable);
    }

    @Override
    public User browseOrganizer(UUID organizerId) {
        //CHECK IS STAFFER IS ASSGINED TO DISABLE THE REQUEST BUTTON
        return userRepo.findByIdAndRole(organizerId,"ORGANIZER_ROLE").orElseThrow(() ->
                new UserNotFoundException("Organizer not found")
        );
    }

    @Override
    public User browseStaffer(UUID stafferId) {
        return userRepo.findByIdAndRole(stafferId,"STAFF_ROLE").orElseThrow(() ->
                new UserNotFoundException("Staffer not found")
        );
    }

    @Override
    public Page<Request> browseRequests(UUID userId, Pageable pageable) {
        return requestRepo.findByReceiverId(userId, pageable);
    }



}
