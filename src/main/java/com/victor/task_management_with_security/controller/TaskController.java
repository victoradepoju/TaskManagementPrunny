package com.victor.task_management_with_security.controller;

import com.victor.task_management_with_security.dto.task.CreateTaskRequest;
import com.victor.task_management_with_security.dto.task.EditTaskRequest;
import com.victor.task_management_with_security.dto.task.TaskResponse;
import com.victor.task_management_with_security.exception_handler.ExceptionResponse;
import com.victor.task_management_with_security.service.task.TaskService;
import com.victor.task_management_with_security.utils.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(
            summary = "Create a new task",
            description = "Create a new task with default completion status set to false."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<Integer> create(
            @RequestBody @Valid CreateTaskRequest taskRequest,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.create(taskRequest, authenticatedUser));
    }

    @PatchMapping("/{taskId}")
    @Operation(
            summary = "Edit a task",
            description = "Edit a task with at not less than 3 characters for title and description. You " +
                    "can also edit you due date! Note, you can only " +
                    "edit your own task."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<TaskResponse> editTask(
            @PathVariable(name = "taskId") Integer taskId,
            @RequestBody @Valid EditTaskRequest taskEditRequest,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.editTask(taskId, taskEditRequest, authenticatedUser));
    }

    @PatchMapping("/status/{task-id}")
    @Operation(
            summary = "Update task completion status",
            description = "Toggle your task completion status to completed and uncompleted, depending " +
                    "on your journey. Note, you can only update your own task"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<Integer> updateTaskStatus(
            @PathVariable("task-id") Integer taskId,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.changeCompletionStatus(taskId, authenticatedUser));
    }

    @DeleteMapping("/delete/{task-id}")
    @Operation(
            summary = "Delete a task",
            description = "Want to permanently remove a task from your archive? This is your guy!"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<Integer> deleteTask (
            @PathVariable("task-id") Integer taskId,
            Authentication authenticatedUser
    ) {
        return ResponseEntity.ok(taskService.deleteTask(taskId, authenticatedUser));
    }

    @GetMapping("{task-id}")
    @Operation(
            summary = "Find task by ID",
            description = "Use a task ID to find the content of the task"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<TaskResponse> findTaskById(
            @PathVariable("task-id") Integer taskId
    ) {
        return ResponseEntity.ok(taskService.findTaskById(taskId));
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all tasks",
            description = "Retrieve all tasks with optional pagination, sorting, and filtering.",
            parameters = {
                    @Parameter(name = "page", description = "The page number to retrieve. Pages are zero-indexed.", example = "0"),
                    @Parameter(name = "size", description = "The number of tasks to retrieve per page.", example = "10"),
                    @Parameter(name = "sortByStatus", description = "Sort tasks by their completion status.", example = "false"),
                    @Parameter(name = "sortByDueDate", description = "Sort tasks by their due date.", example = "false"),
                    @Parameter(name = "filterByStatus", description = "Filter tasks by their completion status.", example = "false"),
                    @Parameter(name = "startDate", description = "The start date to filter tasks. Must be in yyyy-MM-dd format.", example = "2023-01-01"),
                    @Parameter(name = "endDate", description = "The end date to filter tasks. Must be in yyyy-MM-dd format.", example = "2023-12-31")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
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
    @Operation(
            summary = "Retrieve all tasks owned by the authenticated user",
            description = "Retrieve all tasks owned by the authenticated user with optional pagination, sorting, and filtering.",
            parameters = {
                    @Parameter(name = "page", description = "The page number to retrieve. Pages are zero-indexed.", example = "0"),
                    @Parameter(name = "size", description = "The number of tasks to retrieve per page.", example = "10"),
                    @Parameter(name = "sortByStatus", description = "Sort tasks by their completion status.", example = "false"),
                    @Parameter(name = "sortByDueDate", description = "Sort tasks by their due date.", example = "false"),
                    @Parameter(name = "filterByStatus", description = "Filter tasks by their completion status.", example = "false"),
                    @Parameter(name = "startDate", description = "The start date to filter tasks. Must be in yyyy-MM-dd format.", example = "2023-01-01"),
                    @Parameter(name = "endDate", description = "The end date to filter tasks. Must be in yyyy-MM-dd format.", example = "2023-12-31")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
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
