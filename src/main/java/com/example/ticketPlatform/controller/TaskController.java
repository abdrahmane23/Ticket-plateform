package com.example.ticketPlatform.controller;

import com.example.ticketPlatform.domain.Dtos.TaskForOrganizerResponseDto;
import com.example.ticketPlatform.domain.Dtos.TaskForStafferResponseDto;
import com.example.ticketPlatform.domain.entities.Task;
import com.example.ticketPlatform.mappers.TaskMapper;
import com.example.ticketPlatform.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "{eventId}/{ticketTypeId}/staffings")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    public ResponseEntity<Void> AssignStaffer(@PathVariable UUID ticketTypeId,
                                              @RequestParam UUID stafferId,
                                              @AuthenticationPrincipal Jwt jwt) {
        taskService.assignStaffToEvent(ticketTypeId, parseUserId(jwt) , stafferId );
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "{taskId}")
    public ResponseEntity<Void> RemoveStaffer(@PathVariable UUID taskId,
                                              @AuthenticationPrincipal Jwt jwt) {
        taskService.removeTaskFromEvent( parseUserId(jwt),taskId );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("tasksForOrganizer")
    public ResponseEntity<Page<TaskForOrganizerResponseDto>> BrowseTasksForOrganizer(
            @PathVariable UUID ticketTypeId,
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        Page<Task> tasks = taskService.browseTasksForOrganizer(ticketTypeId, parseUserId(jwt), pageable);
        return ResponseEntity.ok(
                tasks.map(taskMapper::toTaskForOrganizerResponseDto)
        );
    }
    @GetMapping("taskForStaffer")
    public ResponseEntity<TaskForStafferResponseDto> BrowseTaskForStaffer(
            @RequestParam UUID StafferId,
            @AuthenticationPrincipal Jwt jwt
    ){
        Task task = taskService.browseTaskForStaffer(StafferId, parseUserId(jwt));
        return ResponseEntity.ok(
                taskMapper.toTaskForStafferResponseDto(task)
        );
    }
    
}
