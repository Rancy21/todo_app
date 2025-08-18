package com.larr.todo.todo_app.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    @Column(name = "date_of_creation")
    private LocalDate dateOfCreation;
    @Column(name = "is_completed")
    private boolean isCompleted = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
