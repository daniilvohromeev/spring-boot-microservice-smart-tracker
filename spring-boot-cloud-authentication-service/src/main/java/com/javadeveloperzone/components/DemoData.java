package com.javadeveloperzone.components;

import com.javadeveloperzone.entity.Role;
import com.javadeveloperzone.entity.User;
import com.javadeveloperzone.repo.RoleRepository;
import com.javadeveloperzone.repo.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoData {
    final RoleRepository roleRepository;
    final UserRepository userRepository;

    final PasswordEncoder bCryptPasswordEncoder;

    public DemoData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @EventListener()
    public void appIsReady(ApplicationReadyEvent event) {
        userRepository.save(User.builder()
                .username("Egorka")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles(List.of(roleRepository.save(Role.builder()
                        .id(1L)
                        .name("ROLE_USER")
                        .build())))
                .build());
    }
}
