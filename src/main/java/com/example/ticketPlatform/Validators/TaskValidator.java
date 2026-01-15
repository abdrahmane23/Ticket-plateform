package com.example.ticketPlatform.Validators;

import com.example.ticketPlatform.Repositories.TaskRepo;
import com.example.ticketPlatform.domain.entities.Task;
import com.example.ticketPlatform.domain.entities.TaskStatusEnum;
import com.example.ticketPlatform.exceptions.EventNotFoundException;
import com.example.ticketPlatform.exceptions.TaskNotFoundException;
import com.example.ticketPlatform.exceptions.TaskOwnershipException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskValidator {
    private final TaskRepo taskRepo;

    public Task getTaskIfValid(UUID taskId) {
        return taskRepo.findByIdAndStatus(taskId, TaskStatusEnum.ACTIVE)
                .orElseThrow(
                        ()-> new TaskNotFoundException("invalid Task"));
    }

    public void VerifyTaskOwnership(Task task, UUID organizerId) {
        boolean isOrganizerOfEvent = task.getTicketType().getEvent().getOrganizer().getId().equals(organizerId);
        if (!isOrganizerOfEvent) {
            throw new TaskOwnershipException("Organizer with ID " + organizerId + " is not the organizer of event with ID " + task.getTicketType().getEvent().getId());
        }
    }
}
