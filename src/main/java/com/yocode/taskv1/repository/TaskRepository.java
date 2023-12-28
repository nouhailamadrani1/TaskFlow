package com.yocode.taskv1.repository;

import com.yocode.taskv1.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Additional custom queries if needed

}
