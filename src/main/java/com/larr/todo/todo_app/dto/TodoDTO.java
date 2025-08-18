package com.larr.todo.todo_app.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TodoDTO {
    private String id;
    private String description;
    private LocalDate dateOfCreation;
    private boolean isCompleted = false;
    private String userEmail;
}
