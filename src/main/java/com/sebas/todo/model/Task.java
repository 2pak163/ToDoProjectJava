
package com.sebas.todo.model;
import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private final String id;
    private String description;
    private boolean completed;
    private final LocalDateTime createdAt;
    
    public Task(String description){
        this.id=UUID.randomUUID().toString();
        this.description=description;
        this.completed=false;
        this.createdAt=LocalDateTime.now();
    }
    
    public Task(){
        this.id=null;
        this.createdAt=null;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
}
