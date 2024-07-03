package com.victor.task_management_with_security.mapper;

import com.victor.task_management_with_security.dto.task.CreateTaskRequest;
import com.victor.task_management_with_security.dto.task.TaskResponse;
import com.victor.task_management_with_security.entity.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TaskMapper {
    public Task fromCreateTaskRequestToTask(CreateTaskRequest createTaskRequest) {
        return Task.builder()
                .title(createTaskRequest.title())
                .description(createTaskRequest.description())
                .status(false) // task completion defaults to false
                .dueDate(parseDueDate(createTaskRequest.dueDate()))
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getOwner().getFirstName(),
                task.isStatus(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getModifiedAt()
        );
    }

    public LocalDate parseDueDate(String dueDateStr) {
        if (dueDateStr == null || dueDateStr.isBlank()) {
            // Default due date is 7 days from creation
            return LocalDate.now().plusDays(7);
        } else {
            // Parse the dueDate string to LocalDate
            return LocalDate.parse(dueDateStr, DateTimeFormatter.ISO_DATE);
        }
    }


    //    public TaskResponse fromTaskToTaskResponse(Task task) {
//        return new TaskResponse(
//                task.getId(),
//                task.getTitle(),
//                task.getDescription(),
//                task.getOwner().getFirstName(),
//                task.isStatus(),
//                task.getDueDate(),
//                task.getCreatedAt(),
//                task.getModifiedAt()
//        );
//    }
}
