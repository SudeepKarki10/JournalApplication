
package com.sudeepkarki.journalApp.controller;

import com.sudeepkarki.journalApp.entity.JournalEntry;
import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.service.JournalEntryService;
import com.sudeepkarki.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<User> saveEntry(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String myId) {
        Optional<User> existingUser = userService.getById(myId);
        if (existingUser.isPresent()) {
            userService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User newUser) {
        // Here, username will be "sudeep"
        // newUser will be the JSON object you send in the request body
        Optional<User> updatedUser = userService.updateEntry(username, newUser);
        return updatedUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // <-- This line is key
    }
}