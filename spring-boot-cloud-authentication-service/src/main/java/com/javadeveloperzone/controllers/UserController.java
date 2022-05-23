package com.javadeveloperzone.controllers;

import com.javadeveloperzone.entity.User;
import com.javadeveloperzone.payroll.UserRequest;
import com.javadeveloperzone.repo.UserRepository;
import com.javadeveloperzone.service.CustomUserDetailService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {


//    final CustomUserDetailService userDetailService;
    final UserRepository userRepository;
    final PasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
//        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


//    public UserController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
//        this.userRepository = userRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRequest newUser) {
        EntityModel<User> entityUser = EntityModel.of(
                userRepository.save(User.builder()
                        .username(newUser.getUsername())
                        .password(newUser.getPassword()).build()));
        return ResponseEntity //
                .created(entityUser.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityUser);
    }

    @GetMapping("/user/{id}")
    public EntityModel<User> userOne(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Пользователь c id: " + id + " не найден"));
        return EntityModel.of(user, linkTo(methodOn(UserController.class).userOne(id)).withSelfRel());
    }
}
