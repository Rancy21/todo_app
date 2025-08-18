package com.larr.todo.todo_app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    @Column(name = "is_active")
    private boolean isActive = true;
    @OneToMany(mappedBy = "user")
    private List<Todo> todos = new ArrayList<>();
}
