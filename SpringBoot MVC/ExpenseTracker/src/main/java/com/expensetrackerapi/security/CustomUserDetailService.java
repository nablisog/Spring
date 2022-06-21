package com.expensetrackerapi.security;

import com.expensetrackerapi.entity.User;
import com.expensetrackerapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found for the email" + email));
        return new org.springframework.security.core.userdetails.User(
                existingUser.getEmail(),existingUser.getPassword(), new ArrayList<>()
        );
    }
}
