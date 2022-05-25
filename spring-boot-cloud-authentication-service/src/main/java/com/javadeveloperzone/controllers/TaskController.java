package com.javadeveloperzone.controllers;

import com.javadeveloperzone.entity.Task;
import com.javadeveloperzone.entity.User;
import com.javadeveloperzone.repo.TaskRepository;
import com.javadeveloperzone.repo.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TaskController {

    final TaskRepository taskRepository;
    final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/tasks/{id}")
    public EntityModel<Task> taskOne(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Задачи с id: " + id + " не найдено")
        );
        return EntityModel.of(task,
                linkTo(methodOn(TaskController.class).taskOne(task.getId())).withSelfRel(),
                linkTo(methodOn(TaskController.class).taskAll()).withRel("tasks"));

    }

    @GetMapping("/tasks/{user_id}")
    public CollectionModel<EntityModel<Task>> taskByUser(@PathVariable Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new RuntimeException("Пользователь с id: " + user_id + " не найден"));
        List<EntityModel<Task>> tasks = taskRepository.findAllByUser(user).stream()
                .map(task -> EntityModel.of(task,
                        linkTo(methodOn(TaskController.class).taskOne(task.getId())).withSelfRel(),
                        linkTo(methodOn(TaskController.class).taskAll()).withRel("tasks")))
                .collect(Collectors.toList());
        return CollectionModel.of(tasks,linkTo(methodOn(TaskController.class).taskByUser(user_id)).withSelfRel());
    }

    @GetMapping("/tasks")
    public CollectionModel<EntityModel<Task>> taskAll() {
        List<EntityModel<Task>> tasks = taskRepository.findAll().stream()
                .map(task -> EntityModel.of(task,
                        linkTo(methodOn(TaskController.class).taskOne(task.getId())).withSelfRel(),
                        linkTo(methodOn(TaskController.class).taskAll()).withRel("tasks")))
                .collect(Collectors.toList());

        return CollectionModel.of(tasks, linkTo(methodOn(UserController.class).userAll()).withSelfRel());

    }
}
