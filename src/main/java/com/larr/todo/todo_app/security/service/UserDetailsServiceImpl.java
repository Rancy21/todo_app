package com.larr.todo.todo_app.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.larr.todo.todo_app.model.User;
import com.larr.todo.todo_app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = repo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword()).authorities(authorities)
                .build();

    }

}
