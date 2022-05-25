package com.javadeveloperzone.controllers;

import com.javadeveloperzone.entity.User;
import com.javadeveloperzone.payroll.UserRequest;
import com.javadeveloperzone.repo.RoleRepository;
import com.javadeveloperzone.repo.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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

    @SneakyThrows
    @GetMapping(value = "/users/{user_id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    EntityModel<File> downloadImage(@PathVariable Long user_id) {
        byte[] image = userRepository.findById(user_id)//
                .orElseThrow(() -> new RuntimeException("Пользователь с id: " + user_id + " не найден")) //
                .getImage().getContent();
        return EntityModel.of(new ByteArrayResource(image).getFile());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> userOne(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Пользователь c id: " + id + " не найден"));
        return EntityModel.of(user, linkTo(methodOn(UserController.class).userOne(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).userAll()).withRel("users"));
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Gets all users", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserController.class)))
                    })
    })
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
