package com.sudeepkarki.journalApp.controller;

import com.sudeepkarki.journalApp.entity.JournalEntry;
import com.sudeepkarki.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username) {
        List<JournalEntry> entries = journalEntryService.getAll(username);
        if (entries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry entry, @PathVariable String username) {
        JournalEntry saved = journalEntryService.saveEntry(entry, username);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @DeleteMapping("/{username}/id/{myId}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String myId, @PathVariable String username) {
        Optional<JournalEntry> existingEntry = journalEntryService.getById(myId);
        if (existingEntry.isPresent()) {
            journalEntryService.deleteById(myId, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{username}/id/{myId}")
    public ResponseEntity<JournalEntry> updateEntry(
            @PathVariable String username,
            @PathVariable String myId,
            @RequestBody JournalEntry newEntry) {
        JournalEntry updatedEntry = journalEntryService.updateEntry(username, myId, newEntry);
        if (updatedEntry != null) {
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
