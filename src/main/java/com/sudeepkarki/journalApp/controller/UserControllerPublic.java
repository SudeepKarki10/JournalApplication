package com.sudeepkarki.journalApp.controller;


import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.repository.UserRepository;
import com.sudeepkarki.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/register")
public class UserControllerPublic {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> saveEntry(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
