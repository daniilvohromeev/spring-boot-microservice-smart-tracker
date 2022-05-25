package com.javadeveloperzone.repo;

import com.javadeveloperzone.entity.Task;
import com.javadeveloperzone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser(User user);
}
