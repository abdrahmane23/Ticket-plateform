package com.example.ticketPlatform.services.impl;
import com.example.ticketPlatform.Repositories.TaskRepo;
import com.example.ticketPlatform.Repositories.TicketTypeRepo;
import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.Validators.StaffLinkValidator;
import com.example.ticketPlatform.Validators.TaskValidator;
import com.example.ticketPlatform.Validators.TicketTypeValidator;
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
    private final TaskValidator taskValidator;
    private final TicketTypeValidator ticketTypeValidator;
    private final StaffLinkValidator staffLinkValidator;

    @Transactional
    @Override
    public void assignStaffToEvent(UUID ticketTypeId, UUID organizerId, UUID stafferId) {
        TicketType assignedticketType = ticketTypeValidator.getTicketTypeIfValid(ticketTypeId, organizerId);
        User staffer = staffLinkValidator.getStafferIfValid(stafferId, organizerId);

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

        Task task = taskValidator.getTaskIfValid(taskId);
        taskValidator.VerifyTaskOwnership(task, organizerId);

        task.setStatus(TaskStatusEnum.CANCELED);
        taskRepo.save(task);
        UUID stafferId = task.getStaffer().getId();
        notificationDispatcher.dispatch(stafferId,
                "Your task for event: " + task.getTicketType().getEvent().getName() + " has been canceled.");
    }

    @Override
    public Page<Task> browseTasksForOrganizer(UUID ticketTypeId, UUID OrganizerId, Pageable pageable) {
        ticketTypeValidator.verifyTicketTypeOwnership(ticketTypeId, OrganizerId);
        return taskRepo.findAllByTicketTypeId(
                ticketTypeId,
                pageable
        );

    }
    @Override
    public Task browseTaskForStaffer(UUID StafferId, UUID OrganizerId) {
        staffLinkValidator.verifyStafferOwnership(StafferId, OrganizerId);
        return taskRepo.findByStafferId(
                StafferId
        ).orElseThrow(
                ()-> new EventNotFoundException("No task found for staffer with ID " + StafferId)
        );
    }

}
