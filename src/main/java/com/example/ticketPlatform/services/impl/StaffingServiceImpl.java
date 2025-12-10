package com.example.ticketPlatform.services.impl;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.Event;
import com.example.ticketPlatform.domain.entities.TicketType;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.EventNotFoundException;
import com.example.ticketPlatform.exceptions.EventTicketException;
import com.example.ticketPlatform.exceptions.StafferNotRegisteredException;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import com.example.ticketPlatform.services.StaffingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffingServiceImpl implements StaffingService {
    private final Keycloak keycloakAdminClient;
    private final UserRepo userRepo;
    private final TicketTypeRepo ticketTypeRepo;
    private final String realm = "event-ticket-plateform";
    private UserRepresentation user;

    @Transactional
    @Override
    public void AssignStaffToEvent(UUID ticketTypeId, UUID organizerId, String StafferUsername) {
        TicketType AssignedticketType = getTicketTypeIfValid(ticketTypeId, organizerId);

        user = keycloakAdminClient.realm(realm)
                    .users()
                    .searchByUsername(StafferUsername,true)
                    .stream()
                    .findFirst()
                    .orElseThrow(
                            ()-> new StafferNotRegisteredException("THIS staffer is not registred")
                    );

        UUID userId = UUID.fromString(user.getId());
        User NewStaffer = userRepo.findById(userId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setId(userId);
                    newUser.setName(user.getUsername());
                    newUser.setEmail(user.getEmail());
                    return newUser;
                });

        NewStaffer.getStaffingEntries().add(AssignedticketType);
        userRepo.save(NewStaffer);

    }

    @Transactional
    public void RemoveStaffFromEvent(UUID ticketTypeId, UUID organizerId, UUID userId) {
        TicketType AssignedTicketType = getTicketTypeIfValid(ticketTypeId, organizerId);
        User Staffer = userRepo.findById(userId)
                .orElseThrow(
                        ()-> new UserNotFoundException("staffer with ID " + userId + " not found"));

        Staffer.getStaffingEntries().remove(AssignedTicketType);
        userRepo.save(Staffer);
    }

    private TicketType getTicketTypeIfValid(UUID ticketTypeId, UUID organizerId) {
        TicketType AssignedTicketType = ticketTypeRepo.findById(ticketTypeId).orElseThrow(
                ()-> new EventNotFoundException("Event with ID " + ticketTypeId + " not found"));
        User organizer=  userRepo.findById(organizerId).orElseThrow(
                ()-> new UserNotFoundException(STR."this organizer with Id\{organizerId}doesnt exist ")
        );
        if (!AssignedTicketType.getEvent().getOrganizer().getId().equals(organizer.getId())) {
            throw new EventTicketException(STR."Organizer with ID \{organizerId} is not the organizer of event with ID \{AssignedTicketType.getEvent().getOrganizer().getId()}");
        }
        return AssignedTicketType;
    }
}
