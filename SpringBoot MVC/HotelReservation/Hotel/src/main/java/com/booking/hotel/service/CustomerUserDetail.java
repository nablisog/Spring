package com.booking.hotel.service;

import com.booking.hotel.model.User;
import com.booking.hotel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class CustomerUserDetail implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsersByUserName(username).get();
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),user.getPassword(),new ArrayList<>());
    }
}
