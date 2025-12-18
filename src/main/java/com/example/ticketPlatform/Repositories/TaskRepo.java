package com.example.ticketPlatform.Repositories;

import com.example.ticketPlatform.domain.entities.Task;
import com.example.ticketPlatform.domain.entities.TaskStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository <Task, UUID> {
    public boolean existsByStafferIdAndStatus(UUID stafferId, TaskStatusEnum status);

    public Optional<Task> findByIdAndStatus(UUID ticketTypeId,TaskStatusEnum status);
}
