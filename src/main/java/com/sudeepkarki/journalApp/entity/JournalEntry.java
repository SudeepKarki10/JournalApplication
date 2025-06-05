package com.sudeepkarki.journalApp.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")    //this will initialize the JournalEntry as a collection in DB
//@Getter
//@Setter
@Data   //It contains @Getter and @Setter along with additional methods
public class JournalEntry {
    @Id  //initializes the id as primary key
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;


    //No need to write these boilerplate code of getter and setter function after using Lombok package as we have defined
    //the getter and setter annotation before class or simply @Data annotation which additionally includes :
    //Getter,
    //Setter,
    //RequiredArgsConstructor,
    //ToString,
    //EqualsAndHashCode,
    //Value
//
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//
//
//
//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {  // fixed method name
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {  // fixed method name
//        this.content = content;
//    }
}
