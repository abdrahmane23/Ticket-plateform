package com.example.ticketPlatform.services.impl;

import com.example.ticketPlatform.Repositories.StaffLinkRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.LinkStatusEnum;
import com.example.ticketPlatform.domain.entities.StaffLink;
import com.example.ticketPlatform.domain.entities.TaskStatusEnum;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.EventTicketException;
import com.example.ticketPlatform.exceptions.StaffLinkNotFoundException;
import com.example.ticketPlatform.services.StafflinkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@AllArgsConstructor
public class StaffLinkServiceImpl implements StafflinkService {
    private final StaffLinkRepo staffLinkRepo;
    private final UserRepo userRepo;

    @Override
    public Page<User> browseStaffers(UUID organizerId, Pageable pageable) {
        return staffLinkRepo.findAllByOrganizerIdAndStatus(organizerId, LinkStatusEnum.ACTIVE, pageable)
                .map(StaffLink::getStaffer);
    }

    @Override
    public void removeLink(UUID stafflinkId, UUID UserId, String RemoverRole) {
        StaffLink staffLink = staffLinkRepo.findByIdAndStatus(stafflinkId, LinkStatusEnum.ACTIVE).orElseThrow(() ->
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

    private void validateStaffLinkOwnership(StaffLink staffLink, UUID UserId, String RemoverRole) {
        if ( (RemoverRole.equals("STAFF_ROLE") && !staffLink.getStaffer().getId().equals(UserId)) ||
                (RemoverRole.equals("ORGANIZER_ROLE") && !staffLink.getOrganizer().getId().equals(UserId)) ) {
            throw new EventTicketException("Staff link not found for this user");
        }
    }

}
