package com.victor.task_management_with_security.controller;

import com.victor.task_management_with_security.dto.task.CreateTaskRequest;
import com.victor.task_management_with_security.dto.task.EditTaskRequest;
import com.victor.task_management_with_security.dto.task.TaskResponse;
import com.victor.task_management_with_security.service.task.TaskService;
import com.victor.task_management_with_security.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid CreateTaskRequest taskRequest,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.create(taskRequest, authenticatedUser));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> editTask(
            @PathVariable(name = "taskId") Integer taskId,
            @RequestBody @Valid EditTaskRequest taskEditRequest,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.editTask(taskId, taskEditRequest, authenticatedUser));
    }

    @PatchMapping("/status/{task-id}")
    public ResponseEntity<Integer> updateTaskStatus(
            @PathVariable("task-id") Integer taskId,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.changeCompletionStatus(taskId, authenticatedUser));
    }

    @DeleteMapping("/delete/{task-id}")
    public ResponseEntity<Integer> deleteTask (
            @PathVariable("task-id") Integer taskId,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.deleteTask(taskId, authenticatedUser));
    }

    @GetMapping("{task-id}")
    public ResponseEntity<TaskResponse> findTaskById(
            @PathVariable("task-id") Integer taskId
    ) {
        return ResponseEntity.ok(taskService.findTaskById(taskId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TaskResponse>> findAllTasks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(required = false, defaultValue = "false") boolean sortByStatus,
            @RequestParam(required = false, defaultValue = "false") boolean sortByDueDate,
            @RequestParam(required = false, defaultValue = "false") boolean filterByStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate
    ) {
        LocalDate startDateLocal = startDate != null ? LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE) : null;
        LocalDate endDateLocal = endDate != null ? LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE) : null;
        return ResponseEntity.ok(taskService.findAllTasks(
                page,
                size,
                sortByStatus,
                sortByDueDate,
                filterByStatus,
                startDateLocal,
                endDateLocal
        ));
    }


    @GetMapping("/owner")
    public ResponseEntity<PageResponse<TaskResponse>> findAllTasksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(required = false, defaultValue = "false") boolean sortByStatus,
            @RequestParam(required = false, defaultValue = "false") boolean sortByDueDate,
            @RequestParam(required = false, defaultValue = "false") boolean filterByStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate,
            Authentication authenticatedUser
    ) {
        LocalDate startDateLocal = startDate != null ? LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE) : null;
        LocalDate endDateLocal = endDate != null ? LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE) : null;
        return ResponseEntity.ok(taskService.findAllTasksByUser(
                page,
                size,
                sortByStatus,
                sortByDueDate,
                filterByStatus,
                startDateLocal,
                endDateLocal,
                authenticatedUser
        ));
    }
}
