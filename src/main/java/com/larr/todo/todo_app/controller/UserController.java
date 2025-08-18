package com.larr.todo.todo_app.controller;

// import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.larr.todo.todo_app.dto.UserDTO;
import com.larr.todo.todo_app.model.User;
import com.larr.todo.todo_app.security.jwt.JWTUtils;
import com.larr.todo.todo_app.service.UserService;
import com.larr.todo.todo_app.service.mapper.UserMapper;

@RestController
@RequestMapping(value = "/api/auth")
public class UserController {
    @Autowired
    private UserService service;

    private UserMapper mapper = new UserMapper();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtils jwtUtils;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUSer(@RequestBody User user) {
        if (service.userExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("User already in use!");
        }
        User savedUser = service.registerUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok(mapper.toUserDTO(savedUser));
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> loginUser(@RequestBody User user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ResponseCookie cookie = jwtUtils.generateJwtCookie(userDetails.getUsername());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(mapper.toUserDTO(service.getUserByEmail(userDetails.getUsername())));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();

        ResponseCookie cleanCookie = jwtUtils.generateCleanJwtCookie();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cleanCookie.toString()).body(
                "{\"message\": \"Logout successful\"}");
    }

}
