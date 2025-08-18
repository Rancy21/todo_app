package com.larr.todo.todo_app.dto;

import java.util.ArrayList;
import java.util.List;

// import com.larr.todo.todo_app.model.Todo

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<TodoDTO> todos = new ArrayList<>();
}
