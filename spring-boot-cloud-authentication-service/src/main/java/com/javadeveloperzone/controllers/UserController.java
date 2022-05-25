package com.javadeveloperzone.controllers;

import com.javadeveloperzone.entity.User;
import com.javadeveloperzone.payroll.UserRequest;
import com.javadeveloperzone.repo.RoleRepository;
import com.javadeveloperzone.repo.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserController {

    final UserRepository userRepository;
    final PasswordEncoder bCryptPasswordEncoder;

    final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users/current")
    public EntityModel<User> currentUser(Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow(
                () -> new RuntimeException("Пользователь с именем: " + principal.getName() + "не найден")
        );
        return EntityModel.of(currentUser,
                linkTo(methodOn(UserController.class).userOne(currentUser.getId())).withSelfRel());
    }

    @PostMapping("/users/registration")
    public ResponseEntity<?> registration(@RequestBody UserRequest newUser) {
        User user = User.builder()
                .username(newUser.getUsername())
                .password(bCryptPasswordEncoder.encode(newUser.getPassword()))
                .roles(List.of(roleRepository.findById(1L).get())).build();
        EntityModel<User> entityUser = EntityModel.of(
                userRepository.save(user),
                linkTo(methodOn(UserController.class).userOne(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).userAll()).withRel("users"));
        return ResponseEntity //
                .created(entityUser.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityUser);
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> userOne(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Пользователь c id: " + id + " не найден"));
        return EntityModel.of(user, linkTo(methodOn(UserController.class).userOne(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).userAll()).withRel("users"));
    }

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> userAll() {
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).userOne(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).userAll()).withRel("users")))
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).userAll()).withSelfRel());
    }
}
