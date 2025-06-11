package com.sudeepkarki.journalApp.service;

import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired  // Properly inject the PasswordEncoder bean
    private PasswordEncoder passwordEncoder;

//    public void saveUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//    }

    public void createUser(User user) throws Exception {
        // Check if user already exists
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUserName(user.getUserName()));
        if (existingUser.isPresent()) {
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



    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }


    public void deleteUser(String username) {
        userRepository.deleteByUserName(username);
    }

    public Optional<User> updateEntry(User newUser) {
//        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUserName(username));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username != null) {
            User userToUpdate = userRepository.findByUserName(username);
            //Now username and password from the DB repository is updated
            userToUpdate.setUserName(newUser.getUserName());
            if (newUser.getPassword() != null && !newUser.getPassword().trim().isEmpty()) {
                userToUpdate.setPassword(passwordEncoder.encode(newUser.getPassword()));
            }
            //Persisted the change on the UserRepository
            userRepository.save(userToUpdate);
            return Optional.of(userToUpdate);
        }
        return Optional.empty();
    }
}