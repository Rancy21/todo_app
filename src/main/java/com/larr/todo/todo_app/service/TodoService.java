package com.larr.todo.todo_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.larr.todo.todo_app.model.Todo;
import com.larr.todo.todo_app.model.User;
import com.larr.todo.todo_app.repository.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repo;

    @Autowired
    private UserService userService;

    public Todo saveTodo(Todo todo) {
        return repo.save(todo);
    }

    public boolean deleteTodo(Todo todo) {
        repo.delete(todo);
        return true;
    }

    public Todo getTodo(Todo todo) {
        return repo.findById(todo.getId()).get();
    }

    public List<Todo> findAllByUser(String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            List<Todo> todos = repo.findAllByUser(user);
            if (todos != null && !todos.isEmpty()) {
                return todos;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean todoExists(Todo todo) {
        return repo.existsById(todo.getId());
    }
}
