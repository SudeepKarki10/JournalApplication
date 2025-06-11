package com.sudeepkarki.journalApp.controller;


import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.repository.UserRepository;
import com.sudeepkarki.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController

public class UserControllerPublic {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> saveEntry(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login() {
        // Authentication is handled by Spring Security Basic Auth
        // If we reach here, authentication was successful
        Map<String, String> response = userService.handleSuccessfulLogin();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


