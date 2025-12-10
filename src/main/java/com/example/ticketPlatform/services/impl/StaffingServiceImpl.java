package com.example.ticketPlatform.services.impl;
import com.example.ticketPlatform.Repositories.EventRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.Event;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.EventNotFoundException;
import com.example.ticketPlatform.exceptions.EventTicketException;
import com.example.ticketPlatform.exceptions.StafferNotRegisteredException;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import com.example.ticketPlatform.services.StaffingService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.models.info.Contact;
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
    private final EventRepo eventRepo;
    private final String realm = "event-ticket-plateform";
    private UserRepresentation user;

    @Transactional
    @Override
    public void AssignStaffToEvent(UUID eventId, UUID organizerId, UUID userId) {
        try {
            user = keycloakAdminClient.realm(realm)
                    .users()
                    .get(userId.toString())
                    .toRepresentation();
        } catch (NotFoundException e) {
            throw new StafferNotRegisteredException("Staffer with ID " + userId + " does not exist");
        }

        Event AssignedEvent = getEventIfValid(eventId, organizerId);

        User NewStaffer = userRepo.findById(userId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setId(userId);
                    newUser.setName(user.getUsername());
                    newUser.setEmail(user.getEmail());
                    return newUser;
                });

        NewStaffer.getStaffingEvents().add(AssignedEvent);
        userRepo.save(NewStaffer);

    }
    @Transactional
    public void RemoveStaffFromEvent(UUID eventId, UUID organizerId, UUID userId) {
        Event AssignedEvent = getEventIfValid(eventId, organizerId);
        User Staffer = userRepo.findById(userId)
                .orElseThrow(
                        ()-> new UserNotFoundException("staffer with ID " + userId + " not found"));

        Staffer.getStaffingEvents().remove(AssignedEvent);
        userRepo.save(Staffer);

    }

    private Event getEventIfValid(UUID eventId, UUID organizerId) {
        Event AssignedEvent = eventRepo.findById(eventId).orElseThrow(
                ()-> new EventNotFoundException("Event with ID " + eventId + " not found"));
        User organizer=  userRepo.findById(organizerId).orElseThrow(
                ()-> new UserNotFoundException(STR."this organizer with Id\{organizerId}doesnt exist ")
        );
        if (!AssignedEvent.getOrganizer().getId().equals(organizer.getId())) {
            throw new EventTicketException(STR."Organizer with ID \{organizerId} is not the organizer of event with ID \{eventId}");
        }
        return AssignedEvent;
    }
}
