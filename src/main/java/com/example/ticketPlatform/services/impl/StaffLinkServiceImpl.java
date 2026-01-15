package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.RequestRepo;
import com.example.ticketPlatform.Repositories.StaffLinkRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Validators.StaffLinkValidator;
import com.example.ticketPlatform.domain.entities.*;
import com.example.ticketPlatform.exceptions.*;
import com.example.ticketPlatform.services.NotificationDispatcher;
import com.example.ticketPlatform.services.NotificationService;
import com.example.ticketPlatform.services.StafflinkService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@AllArgsConstructor
public class StaffLinkServiceImpl implements StafflinkService {
    private final StaffLinkRepo staffLinkRepo;
    private final NotificationDispatcher notificationDispatcher;
    private final StaffLinkValidator staffLinkValidator;
    @Override
    public Page<User> browseStaffers(UUID organizerId, Pageable pageable) {
        return staffLinkRepo.findAllByOrganizerIdAndStatus(organizerId, LinkStatusEnum.ACTIVE, pageable)
                .map(StaffLink::getStaffer);
    }

    @Override
    public void createLink(Request request, String receiverRole) {
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
        staffLinkValidator.isStafferLinked(staffer.getId());
        StaffLink staffLink = new StaffLink();
        staffLink.setOrganizer(organizer);
        staffLink.setStaffer(staffer);
        staffLink.setStatus(LinkStatusEnum.ACTIVE);
        staffLinkRepo.save(staffLink);
        notificationDispatcher.dispatch(request.getSender().getId(),
                "Your request to link with " + request.getReceiver().getName() + " has been accepted.");
    }

    @Override
    @Transactional
    public void removeLink(UUID stafflinkId, UUID UserId, String RemoverRole) {
        StaffLink staffLink = staffLinkValidator.getStaffLinkIfExists(stafflinkId);

        staffLinkValidator.validateStaffLinkOwnership(staffLink, UserId, RemoverRole);

        staffLink.getStaffer().getTasks().forEach(task -> {
            if (task.getStatus() == TaskStatusEnum.ACTIVE) {
                task.setStatus(TaskStatusEnum.INTERUPTED);
            }
        });
        staffLink.setStatus(LinkStatusEnum.FINISHED);
        staffLinkRepo.save(staffLink);
        UUID stafferId = staffLink.getStaffer().getId();
        String organizerName = staffLink.getOrganizer().getName();
        notificationDispatcher.dispatch(stafferId, "Your staff link with " + organizerName +" has been removed.");

    }

    @Override
    public User browseStaffer(UUID organizerId,UUID StafferId) {
        return staffLinkRepo.findByOrganizerIdAndStatusAndStafferId(organizerId, LinkStatusEnum.ACTIVE,StafferId)
                .getStaffer();
    }

    @Override
    public User browseOrganizer(UUID StafferId) {
        return staffLinkRepo.findByStatusAndStafferId(LinkStatusEnum.ACTIVE,StafferId)
                .getOrganizer();

    }

}
