package com.victor.task_management_with_security.repository;

import com.victor.task_management_with_security.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findAllByDueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Task> findAllByStatus(boolean status, Pageable pageable);
    Page<Task> findAllByDueDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, boolean status, Pageable pageable);

    Page<Task> findAllByOwnerId(Integer ownerId, Pageable pageable);
    Page<Task> findAllByOwnerIdAndStatus(Integer ownerId, boolean status, Pageable pageable);
    Page<Task> findAllByOwnerIdAndDueDateBetween(
            Integer ownerId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}
