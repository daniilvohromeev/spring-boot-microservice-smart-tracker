package com.javadeveloperzone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user-me")
    public String me(Principal principal){
        return principal.toString();
    }

}
