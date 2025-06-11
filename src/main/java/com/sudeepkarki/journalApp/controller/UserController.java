
package com.sudeepkarki.journalApp.controller;

import com.sudeepkarki.journalApp.entity.JournalEntry;
import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.service.JournalEntryService;
import com.sudeepkarki.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;
/*

//These are previous methods which were coded before the authentication and authorization part
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> entries = userService.getAll();
        if (entries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<User> getUserById(@PathVariable String myId) {
        Optional<User> users = userService.getById(myId);
        return users.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    } */

    @DeleteMapping
    public ResponseEntity<Void> deleteEntryByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username != null) {
            userService.deleteUser(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> updatedUser = null;
        if (username != null) {
            updatedUser = userService.updateEntry(newUser);
        }
        return updatedUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // <-- This line is key
    }
}