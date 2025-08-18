package com.larr.todo.todo_app.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.larr.todo.todo_app.model.User;
import com.larr.todo.todo_app.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User registerUser(User user) {
        // Check if the user exists
        if (repository.findUserByEmail(user.getEmail()).isPresent()) {
            return null;
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);

        return repository.save(user);
    }

    public User loginUser(String email, String password) {
        Optional<User> existingUser = repository.findUserByEmail(email);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public User getUserByEmail(String email) {
        return repository.findUserByEmail(email).get();
    }

    public boolean userExists(String email) {
        if (repository.findUserByEmail(email).isPresent()) {
            return true;
        }

        return false;
    }

}
