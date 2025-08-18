package com.larr.todo.todo_app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.util.MethodInvocationRecorder.Recorded.ToCollectionConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.larr.todo.todo_app.model.Todo;
// import com.larr.todo.todo_app.model.User;
import com.larr.todo.todo_app.service.TodoService;
import com.larr.todo.todo_app.service.UserService;
import com.larr.todo.todo_app.service.mapper.TodoMapper;

@RestController
@RequestMapping(value = "/api/todo")
public class TodoController {
    @Autowired
    private TodoService service;

    @Autowired
    private UserService userService;

    private TodoMapper mapper = new TodoMapper();

    Logger logger = LoggerFactory.getLogger(TodoController.class);

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveTodo(@RequestBody Todo todo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails user = (UserDetails) authentication.getPrincipal();
        todo.setUser(userService.getUserByEmail(user.getUsername()));
        todo.setDateOfCreation(LocalDate.now());
        Todo savedTodo = service.saveTodo(todo);
        if (savedTodo != null) {
            return ResponseEntity.ok(mapper.toTodoDTO(savedTodo));
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> DeleteTodo(@RequestBody Todo todo) {
        Todo deletedTodo = service.getTodo(todo);
        if (service.deleteTodo(deletedTodo)) {
            return ResponseEntity.ok(mapper.toTodoDTO(deletedTodo));
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getTodosByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Current auth: " + authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Todo> todos = new ArrayList<>();
        todos = service.findAllByUser(userDetails.getUsername());
        if (todos != null && !todos.isEmpty()) {
            return ResponseEntity.ok(todos.stream().map(mapper::toTodoDTO).collect(Collectors.toList()));
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateTodo(@RequestBody Todo todo) {
        if (service.todoExists(todo)) {
            Todo theTodo = service.getTodo(todo);
            theTodo.setCompleted(todo.isCompleted());
            theTodo.setDateOfCreation(todo.getDateOfCreation());
            theTodo.setDescription(todo.getDescription());

            Todo updatedTodo = service.saveTodo(theTodo);
            if (updatedTodo != null) {
                return ResponseEntity.ok(mapper.toTodoDTO(updatedTodo));
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return ResponseEntity.badRequest().body("Todo not found");
        }
    }

}
