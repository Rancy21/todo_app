package com.larr.todo.todo_app.service.mapper;

import com.larr.todo.todo_app.dto.TodoDTO;
import com.larr.todo.todo_app.model.Todo;

public class TodoMapper {
    public TodoDTO toTodoDTO(Todo todo) {
        if (todo == null) {
            return null;
        }
        TodoDTO dto = new TodoDTO();
        dto.setCompleted(todo.isCompleted());
        dto.setDateOfCreation(todo.getDateOfCreation());
        dto.setDescription(todo.getDescription());
        dto.setId((todo.getId()));
        dto.setUserEmail(todo.getUser().getEmail());
        return dto;
    }
}