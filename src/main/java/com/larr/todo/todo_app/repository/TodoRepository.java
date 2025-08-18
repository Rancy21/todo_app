package com.larr.todo.todo_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.larr.todo.todo_app.model.Todo;
import com.larr.todo.todo_app.model.User;

public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findAllByUser(User user);
}
