package com.javadeveloperzone.repo;

import com.javadeveloperzone.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}