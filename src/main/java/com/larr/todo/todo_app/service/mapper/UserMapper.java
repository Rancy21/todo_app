package com.larr.todo.todo_app.service.mapper;

// import java.util.stream.Collector
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired

import com.larr.todo.todo_app.dto.UserDTO;
import com.larr.todo.todo_app.model.User;

public class UserMapper {
    private TodoMapper todoMapper = new TodoMapper();

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setTodos(user.getTodos().stream().map(todoMapper::toTodoDTO).collect(Collectors.toList()));
        return dto;
    }
}
