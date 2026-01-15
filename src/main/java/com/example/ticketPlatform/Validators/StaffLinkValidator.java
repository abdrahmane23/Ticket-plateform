package com.example.ticketPlatform.Validators;

import com.example.ticketPlatform.Repositories.StaffLinkRepo;
import com.example.ticketPlatform.domain.entities.LinkStatusEnum;
import com.example.ticketPlatform.domain.entities.StaffLink;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StaffLinkValidator {
    
    private final StaffLinkRepo staffLinkRepo;
    private final UserValidator userValidator;
    
    public void isStafferLinked( UUID stafferId) {
         if (staffLinkRepo.existsByStafferIdAndStatus(stafferId, LinkStatusEnum.ACTIVE)){
             throw new StafferAlreadyLinkedException("Staffer with ID " + stafferId + " is already assigned to an active link");
         }

    }
    public StaffLink getStaffLinkIfExists( UUID stafflinkId) {
        return staffLinkRepo.findByIdAndStatus(stafflinkId, LinkStatusEnum.ACTIVE).orElseThrow(() ->
                new StaffLinkNotFoundException("Staff link not found")
        );
    }
    public void validateStaffLinkOwnership(StaffLink staffLink, UUID UserId, String RemoverRole) {
        if ( (RemoverRole.equals("STAFF_ROLE") && !staffLink.getStaffer().getId().equals(UserId)) ||
                (RemoverRole.equals("ORGANIZER_ROLE") && !staffLink.getOrganizer().getId().equals(UserId)) ) {
            throw new StaffLinkOwnershipException("Staff link not found for this user");
        }
    }
    public User getStafferIfValid(UUID stafferId, UUID organizerId) {
        User staffer = userValidator.getUserIfExists(stafferId ,"STAFFER");
        StaffLink staffLink = staffer.getStaffLink();
        if(staffLink==null || !staffLink.getOrganizer().getId().equals(organizerId)){
            throw new StafferNotRegisteredException("Staffer with ID " + stafferId + " is not registered under organizer with ID " + organizerId);
        }
        return staffer;
    }
    public void verifyStafferOwnership(UUID stafferId, UUID organizerId) {
        User staffer = userValidator.getUserIfExists(stafferId,"STAFFER");
        StaffLink staffLink = staffer.getStaffLink();
        if(staffLink==null || !staffLink.getOrganizer().getId().equals(organizerId)){
            throw new StafferNotRegisteredException("Staffer with ID " + stafferId + " is not registered under organizer with ID " + organizerId);
        }
    }
}
