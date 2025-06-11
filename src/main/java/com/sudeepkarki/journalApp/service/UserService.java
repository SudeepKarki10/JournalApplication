package com.sudeepkarki.journalApp.service;

import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Save user without encoding password (used internally when password is already encoded)
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Create a new user with validation and password encoding
     */
    @Transactional
    public void createUser(User user) throws Exception {
        // Check if user already exists
        User existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser != null) {
            throw new Exception("User with username '" + user.getUserName() + "' already exists");
        }

        // Validate required fields
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new Exception("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new Exception("Password cannot be null or empty");
        }

        // Set default role if roles is null or empty
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Arrays.asList("USER"));
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Find user by username
     */
    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    /**
     * Delete user by username
     */
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUserName(username);
    }

    /**
     * Update user information for the currently authenticated user
     */
    @Transactional
    public Optional<User> updateEntry(User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username != null) {
            User userToUpdate = userRepository.findByUserName(username);
            if (userToUpdate != null) {
                // Update username if provided
                if (newUser.getUserName() != null && !newUser.getUserName().trim().isEmpty()) {
                    userToUpdate.setUserName(newUser.getUserName());
                }

                // Update password if provided
                if (newUser.getPassword() != null && !newUser.getPassword().trim().isEmpty()) {
                    userToUpdate.setPassword(passwordEncoder.encode(newUser.getPassword()));
                }

                // Save updated user
                userRepository.save(userToUpdate);
                return Optional.of(userToUpdate);
            }
        }
        return Optional.empty();
    }

    /**
     * Handle successful login and return response
     */
    public Map<String, String> handleSuccessfulLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("username", username);
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return response;
    }
}