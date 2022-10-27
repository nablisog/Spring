package com.springsecurity.controller;

import com.springsecurity.authenticationRequest.AuthRequest;
import com.springsecurity.model.User;
import com.springsecurity.repository.UserRepository;
import com.springsecurity.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class WelcomeController {
    private UserRepository repository;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public List<User> home(){
        return repository.findAll();
    }

    @PostMapping("/add")
    public User adduser(@RequestBody User user){
        User u = new User();
        u.setEmail(user.getEmail());
        String password = passwordEncoder.encode(user.getPassword());
        u.setPassword(password);
        u.setUserName(user.getUserName());
        repository.save(u);
        return u;
    }



    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUserName(), (authRequest).getPassword()));
        } catch (Exception e) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }




}
