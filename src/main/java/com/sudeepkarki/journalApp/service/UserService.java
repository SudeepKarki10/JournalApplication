package com.sudeepkarki.journalApp.service;

import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

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

        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(String id) {
        return userRepository.findById(new ObjectId(id));
    }

    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }


    public void deleteById(String id) {
        userRepository.deleteById(new ObjectId(id));
    }

    public Optional<User> updateEntry(String username, User newUser) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUserName(username));
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUserName(newUser.getUserName());
            userToUpdate.setPassword(newUser.getPassword());
            userRepository.save(userToUpdate);
            return Optional.of(userToUpdate);
        }
        return Optional.empty();
    }
}
