package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.RequestRepo;
import com.example.ticketPlatform.Repositories.StaffLinkRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.*;
import com.example.ticketPlatform.exceptions.*;
import com.example.ticketPlatform.services.NotificationDispatcher;
import com.example.ticketPlatform.services.RequestingService;
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
    @Transactional
    @Override
    public void sendrequest(UUID senderId, UUID receiverId, String senderRole) {
        User sender = userRepo.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Sender with %s not found", senderId)));
        User receiver = userRepo.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException(String.format("receiver with %s not found", receiverId)));

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
        Request request = requestRepo.findById(requestId).
                orElseThrow(() -> new RequestNotFoundException("Request not found"));

        validateRequestOwnership(request, uuid);

        User staffer;
        User organizer;
        if (receiverRole.equals("STAFF_ROLE") ){
            staffer = request.getReceiver();
            organizer = request.getSender();
        } else if (receiverRole.equals("ORGANIZER_ROLE") ){
            staffer = request.getSender();
            organizer = request.getReceiver();
        } else {
            throw new IvalideRoleException("Invalid receiver role");

        }

        if (staffLinkRepo.existsByStafferIdAndStatus(staffer.getId(), LinkStatusEnum.ACTIVE)) {
            throw new StafferAlreadyLinkedException("Staffer with ID " + staffer.getId() + " is already assigned to an active link");
        }
        StaffLink staffLink = new StaffLink();
        staffLink.setOrganizer(organizer);
        staffLink.setStaffer(staffer);
        staffLink.setStatus(LinkStatusEnum.ACTIVE);
        staffLinkRepo.save(staffLink);
        requestRepo.delete(request);
        notificationDispatcher.dispatch(request.getSender().getId(),
                "Your request to link with " + request.getReceiver().getName() + " has been accepted.");
    }

    @Override
    public void rejectrequest(UUID requestId, UUID uuid) {
        Request request = requestRepo.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException("Request not found")
        );
        validateRequestOwnership(request, uuid);
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

    private void validateRequestOwnership(Request request, UUID userId) {
        if (!request.getReceiver().getId().equals(userId)) {
            throw new RequestOwnershipException("Request not found for this user");
        }
    }


}
