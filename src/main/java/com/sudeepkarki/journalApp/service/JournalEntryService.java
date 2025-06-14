package com.sudeepkarki.journalApp.service;

import com.sudeepkarki.journalApp.entity.JournalEntry;
import com.sudeepkarki.journalApp.entity.User;
import com.sudeepkarki.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        // Set the date automatically
        journalEntry.setDate(LocalDateTime.now());

        // Save the entry first
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);

        // Find the user
        Optional<User> userOptional = userService.findByUserName(userName);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found: " + userName);
        }

        // Update user's journal entries and save
        User user = userOptional.get();
        user.getJournalEntries().add(savedEntry);
        userService.saveUser(user);

        return savedEntry;
    }

    public List<JournalEntry> getAll(String username) {
        Optional<User> optionalUser = userService.findByUserName(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found: " + username);
        }

        User user = optionalUser.get();
        return user.getJournalEntries();

//        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(String id) {
        return journalEntryRepository.findById(new ObjectId(id));
    }

    @Transactional
    public void deleteById(String id, String username) {
        ObjectId objectId = new ObjectId(id);

        // First, remove the reference from user's journal entries list
        Optional<User> userOptional = userService.findByUserName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<JournalEntry> journalEntries = user.getJournalEntries();
            // Remove the journal entry with matching ObjectId
            journalEntries.removeIf(entry -> entry.getId().equals(objectId));

            // Save the updated user
            userService.saveUser(user);
        }
        // Then delete the journal entry from the collection
        journalEntryRepository.deleteById(objectId);
    }


    public JournalEntry updateEntry(String username, String id, JournalEntry newEntry) {
        Optional<JournalEntry> oldEntryOptional = journalEntryRepository.findById(new ObjectId(id));
        Optional<User> optionalUser = userService.findByUserName(username);
        if (optionalUser.isPresent() && oldEntryOptional.isPresent()) {
            JournalEntry existingEntry = oldEntryOptional.get();
            existingEntry.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(newEntry.getContent() != null ? newEntry.getContent() : existingEntry.getContent());
            return journalEntryRepository.save(existingEntry);
        }
        return null;
    }

    public static class UserDetailsServiceImpl {


    }
}
