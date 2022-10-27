package com.springsecurity.service;

import com.springsecurity.model.User;
import com.springsecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUsersByUserName(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), new ArrayList<>());
    }
}
