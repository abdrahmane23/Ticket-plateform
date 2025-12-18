package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.RequestRepo;
import com.example.ticketPlatform.Repositories.StaffLinkRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.*;
import com.example.ticketPlatform.exceptions.*;
import com.example.ticketPlatform.services.RequestingService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Link;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RequestingServiceImpl implements RequestingService {
    private final UserRepo userRepo;
    private final RequestRepo requestRepo;
    private final StaffLinkRepo staffLinkRepo;
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
            throw new EventTicketException("Staffer with ID " + senderId + " is already on link, cannot send request");
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
            throw new EventTicketException("Invalid receiver role");

        }

        if (staffLinkRepo.existsByStafferIdAndStatus(staffer.getId(), LinkStatusEnum.ACTIVE)) {
            throw new EventTicketException("Staffer with ID " + staffer.getId() + " is already assigned to an active link");
        }
        StaffLink staffLink = new StaffLink();
        staffLink.setOrganizer(organizer);
        staffLink.setStaffer(staffer);
        staffLink.setStatus(LinkStatusEnum.ACTIVE);
        staffLinkRepo.save(staffLink);
        requestRepo.delete(request);
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
    public void removeLink(UUID stafflinkId, UUID UserId, String RemoverRole) {
        StaffLink staffLink = staffLinkRepo.findByIdAndStatus(stafflinkId,LinkStatusEnum.ACTIVE).orElseThrow(() ->
                new StaffLinkNotFoundException("Staff link not found")
        );

        validateStaffLinkOwnership(staffLink, UserId, RemoverRole);

        staffLink.getStaffer().getTasks().forEach(task -> {
            if (task.getStatus() == TaskStatusEnum.ACTIVE) {
                task.setStatus(TaskStatusEnum.INTERUPTED);
            }
        });
        staffLink.setStatus(LinkStatusEnum.FINISHED);
        staffLinkRepo.save(staffLink);
    }
    private void validateRequestOwnership(Request request, UUID userId) {
        if (!request.getReceiver().getId().equals(userId)) {
            throw new EventTicketException("Request not found for this user");
        }
    }
    private void validateStaffLinkOwnership(StaffLink staffLink, UUID UserId, String RemoverRole) {
        if ( (RemoverRole.equals("STAFF_ROLE") && !staffLink.getStaffer().getId().equals(UserId)) ||
                (RemoverRole.equals("ORGANIZER_ROLE") && !staffLink.getOrganizer().getId().equals(UserId)) ) {
            throw new EventTicketException("Staff link not found for this user");
        }
    }

}
