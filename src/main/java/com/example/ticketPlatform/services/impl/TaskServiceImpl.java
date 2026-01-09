package com.example.ticketPlatform.services.impl;
import com.example.ticketPlatform.Repositories.TaskRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.*;
import com.example.ticketPlatform.exceptions.*;
import com.example.ticketPlatform.services.NotificationDispatcher;
import com.example.ticketPlatform.services.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final UserRepo userRepo;
    private final TicketTypeRepo ticketTypeRepo;
    private final TaskRepo taskRepo;
    private final NotificationDispatcher notificationDispatcher;

    @Transactional
    @Override
    public void assignStaffToEvent(UUID ticketTypeId, UUID organizerId, UUID stafferId) {
        TicketType assignedticketType = getTicketTypeIfValid(ticketTypeId, organizerId);
        User staffer = getStafferIfValid(stafferId, organizerId);

        if (taskRepo.existsByStafferIdAndStatus(stafferId, TaskStatusEnum.ACTIVE)) {
            throw new EventTicketException("Staffer with ID " + stafferId + " is already assigned to an active task");
        }
        Task task = new Task();
        task.setTicketType(assignedticketType);
        task.setStaffer(staffer);
        task.setStatus(TaskStatusEnum.ACTIVE);
        taskRepo.save(task);
        notificationDispatcher.dispatch(stafferId,
                "You have been assigned to a new task for event: " + assignedticketType.getEvent().getName());

    }

    @Transactional
    @Override
    public void removeTaskFromEvent( UUID organizerId, UUID taskId) {
        Task task = taskRepo.findByIdAndStatus(taskId,TaskStatusEnum.ACTIVE)
                .orElseThrow(
                        ()-> new EventNotFoundException("invalid Task"));

        boolean isOrganizerOfEvent = task.getTicketType().getEvent().getOrganizer().getId().equals(organizerId);
        if (!isOrganizerOfEvent) {
            throw new EventTicketException("Organizer with ID " + organizerId + " is not the organizer of event with ID " + task.getTicketType().getEvent().getId());
        }

        task.setStatus(TaskStatusEnum.CANCELED);
        taskRepo.save(task);
        UUID stafferId = task.getStaffer().getId();
        notificationDispatcher.dispatch(stafferId,
                "Your task for event: " + task.getTicketType().getEvent().getName() + " has been canceled.");
    }

    @Override
    public Page<Task> browseTasksForOrganizer(UUID ticketTypeId, UUID OrganizerId, Pageable pageable) {
        verifyTicketTypeOwnership(ticketTypeId, OrganizerId);
        return taskRepo.findAllByTicketTypeId(
                ticketTypeId,
                pageable
        );

    }
    @Override
    public Task browseTaskForStaffer(UUID StafferId, UUID OrganizerId) {
        verifyStafferOwnership(StafferId, OrganizerId);
        return taskRepo.findByStafferId(
                StafferId
        ).orElseThrow(
                ()-> new EventNotFoundException("No task found for staffer with ID " + StafferId)
        );
    }


    private TicketType getTicketTypeIfValid(UUID ticketTypeId, UUID organizerId) {
        TicketType AssignedTicketType = ticketTypeRepo.findById(ticketTypeId).orElseThrow(
                ()-> new TicketTypeNotFoundException("Event with ID " + ticketTypeId + " not found"));
        User organizer=  userRepo.findById(organizerId).orElseThrow(
                ()-> new UserNotFoundException(STR."this organizer with Id\{organizerId}doesnt exist ")
        );
        if (!AssignedTicketType.getEvent().getOrganizer().equals(organizer)) {
            throw new EventTicketException(STR."Organizer with ID \{organizerId} is not the organizer of event with ID \{AssignedTicketType.getEvent().getOrganizer().getId()}");
        }
        return AssignedTicketType;
    }

    private User getStafferIfValid(UUID stafferId, UUID organizerId) {
        User staffer = userRepo.findById(stafferId)
                .orElseThrow(
                        ()-> new UserNotFoundException("staffer with ID " + stafferId + " not found"));
        StaffLink staffLink = staffer.getStaffLink();
        if(staffLink==null || !staffLink.getOrganizer().getId().equals(organizerId)){
            throw new StafferNotRegisteredException("Staffer with ID " + stafferId + " is not registered under organizer with ID " + organizerId);
        }
        return staffer;
    }
    private void verifyTicketTypeOwnership(UUID ticketTypeId, UUID organizerId) {
        TicketType AssignedTicketType = ticketTypeRepo.findById(ticketTypeId).orElseThrow(
                ()-> new TicketTypeNotFoundException("Event with ID " + ticketTypeId + " not found"));
        User organizer=  userRepo.findById(organizerId).orElseThrow(
                ()-> new UserNotFoundException(STR."this organizer with Id\{organizerId}doesnt exist ")
        );
        if (!AssignedTicketType.getEvent().getOrganizer().equals(organizer)) {
            throw new EventTicketException(STR."Organizer with ID \{organizerId} is not the organizer of event with ID \{AssignedTicketType.getEvent().getOrganizer().getId()}");
        }
    }
    private void verifyStafferOwnership(UUID stafferId, UUID organizerId) {
        User staffer = userRepo.findById(stafferId)
                .orElseThrow(
                        ()-> new UserNotFoundException("staffer with ID " + stafferId + " not found"));
        StaffLink staffLink = staffer.getStaffLink();
        if(staffLink==null || !staffLink.getOrganizer().getId().equals(organizerId)){
            throw new StafferNotRegisteredException("Staffer with ID " + stafferId + " is not registered under organizer with ID " + organizerId);
        }
    }
}
