package com.sudeepkarki.journalApp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
//    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getAll() {
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @GetMapping("id/{myId}")
//    public JournalEntry getJournalEntryById(@PathVariable long myId) {
//        return journalEntries.get(myId);
//    }
//
//    @PostMapping
//    public JournalEntry createEntry(@RequestBody JournalEntry entry) {
//        journalEntries.put(entry.getId(), entry);
//        return entry;
//    }
//
//    @DeleteMapping("id/{myId}")
//    public JournalEntry deleteEntry(@PathVariable long myId) {
//        return journalEntries.remove(myId);
//    }
//
//    @PutMapping("id/{myId}")
//    public JournalEntry updateEntry(@PathVariable long myId, @RequestBody JournalEntry myEntry) {
//        return journalEntries.put(myId, myEntry);
//    }
}
