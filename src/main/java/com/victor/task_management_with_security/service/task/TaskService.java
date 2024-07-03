package com.victor.task_management_with_security.service.task;

import com.victor.task_management_with_security.dto.task.CreateTaskRequest;
import com.victor.task_management_with_security.dto.task.EditTaskRequest;
import com.victor.task_management_with_security.dto.task.TaskResponse;
import com.victor.task_management_with_security.entity.Task;
import com.victor.task_management_with_security.entity.User;
import com.victor.task_management_with_security.exception.NotPermittedException;
import com.victor.task_management_with_security.mapper.TaskMapper;
import com.victor.task_management_with_security.repository.TaskRepository;
import com.victor.task_management_with_security.utils.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Integer create(
            CreateTaskRequest createTaskRequest,
            Authentication authenticatedUser
    ) {
        User user = (User) authenticatedUser.getPrincipal();

        Task task = taskMapper.fromCreateTaskRequestToTask(createTaskRequest);
        task.setOwner(user);
        return taskRepository.save(task).getId();
    }

    public TaskResponse editTask(
            Integer taskId,
            EditTaskRequest taskEditRequest,
            Authentication authenticatedUser
    ) {
        User user = (User) authenticatedUser.getPrincipal();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("No Task found"));

        if (!Objects.equals(user.getId(), task.getOwner().getId())) {
            throw new NotPermittedException(
                    "You cannot edit a task that belongs to another user!"
            );
        }

        if (taskEditRequest.title() != null) {
            task.setTitle(taskEditRequest.title());
        }
        if (taskEditRequest.description() != null) {
            task.setDescription(taskEditRequest.description());
        }
        if (taskEditRequest.dueDate() != null) {
            task.setDueDate(taskMapper.parseDueDate(taskEditRequest.dueDate()));
        }

        taskRepository.save(task);

        return taskMapper.toTaskResponse(task);
    }

    public Integer changeCompletionStatus(
            Integer taskId,
            Authentication authenticatedUser
    ) {
        User user = (User) authenticatedUser.getPrincipal();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (!Objects.equals(user.getId(), task.getOwner().getId())) {
            throw new NotPermittedException(
                    "You can only change the completion status of your own task"
            );
        }

        task.setStatus(!task.isStatus());

        taskRepository.save(task);

        return taskId;
    }

    public Integer deleteTask(Integer taskId, Authentication authenticatedUser) {
        User user = (User) authenticatedUser.getPrincipal();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (!Objects.equals(user.getId(), task.getOwner().getId())) {
            throw new NotPermittedException(
                    "You can only delete your own task"
            );
        }

        taskRepository.delete(task);
        return taskId;
    }

    public TaskResponse findTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new EntityNotFoundException("Task not found"));

        return taskMapper.toTaskResponse(task);
    }

    public PageResponse<TaskResponse> findAllTasks(
            int page,
            int size,
            boolean sortByStatus,
            boolean sortByDueDate,
            boolean filterByStatus,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Sort sort = Sort.by("createdAt").ascending(); // Default sorting by createdAt
        if (sortByDueDate) {
            sort = Sort.by("dueDate").and(Sort.by("createdAt").ascending());
        }
        if (sortByStatus) {
            sort = Sort.by("status").and(Sort.by("dueDate")).ascending().and(Sort.by("createdAt").ascending());
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        // Page takes a generic of the object we want to paginate
        Page<Task> tasksPage;
        if (startDate != null && endDate != null) {
            if (filterByStatus) {
                tasksPage = taskRepository.findAllByDueDateBetweenAndStatus(
                        startDate,
                        endDate,
                        true,
                        pageable
                );
            } else {
                tasksPage = taskRepository.findAllByDueDateBetween(
                        startDate,
                        endDate,
                        pageable
                );
            }
        } else if (filterByStatus) {
            tasksPage = taskRepository.findAllByStatus(
                    true,
                    pageable
            );
        } else {
            tasksPage = taskRepository.findAll(pageable);
        }

        List<TaskResponse> taskResponseList = tasksPage.stream()
                .map(taskMapper::toTaskResponse)
                .toList();

        return new PageResponse<>(
                taskResponseList,
                tasksPage.getNumber(),
                tasksPage.getSize(),
                tasksPage.getTotalElements(),
                tasksPage.getTotalPages(),
                tasksPage.isFirst(),
                tasksPage.isLast()
        );
    }

    public PageResponse<TaskResponse> findAllTasksByUser(
            int page,
            int size,
            boolean sortByStatus,
            boolean sortByDueDate,
            boolean filterByStatus,
            LocalDate startDate,
            LocalDate endDate,
            Authentication authenticatedUser
    ) {
        Sort sort = Sort.by("createdAt").ascending(); // Default sorting by createdAt
        if (sortByDueDate) {
            sort = Sort.by("dueDate").and(Sort.by("createdAt")).ascending();
        }
        if (sortByStatus) {
            sort = Sort.by("status").and(Sort.by("createdAt")).ascending(); // Sort by completion status and then by createdAt
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        var userId = ((User) authenticatedUser.getPrincipal()).getId();

        Page<Task> tasksPage;
        if (startDate != null && endDate != null) {
            tasksPage = taskRepository.findAllByOwnerIdAndDueDateBetween(userId, startDate, endDate, pageable);
        } else if (filterByStatus) {
            tasksPage = taskRepository.findAllByOwnerIdAndStatus(
                    userId,
                    true,
                    pageable);
        } else {
            tasksPage = taskRepository.findAllByOwnerId(userId, pageable);
        }

//        Page<Task> tasksPage = taskRepository
//                .findAllByUserId(userId, pageable);

        List<TaskResponse> taskResponseList = tasksPage.stream()
                .map(taskMapper::toTaskResponse)
                .toList();

        return new PageResponse<>(
                taskResponseList,
                tasksPage.getNumber(),
                tasksPage.getSize(),
                tasksPage.getTotalElements(),
                tasksPage.getTotalPages(),
                tasksPage.isFirst(),
                tasksPage.isLast()
        );
    }
}
