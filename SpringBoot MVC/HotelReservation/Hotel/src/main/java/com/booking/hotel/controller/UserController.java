package com.booking.hotel.controller;


import com.booking.hotel.authentication.AuthRequest;
import com.booking.hotel.model.User;
import com.booking.hotel.service.UserService;
import com.booking.hotel.utility.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/ListOfAllUsers")
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/activeUsers")
    public List<User>activeUsers(){
        return userService.findActiveUsers();
    }

    @GetMapping("/fetchUserByUserName/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable("username")String username) throws  Exception {
        User user = userService.findUserUsername(username);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> userSignup(@Valid @RequestBody User user) throws Exception {
        User signup = userService.register(user);
        return new ResponseEntity<>(signup, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody AuthRequest authRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        } catch (Exception e) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id")String id,@Valid @RequestBody User user) {
        User updateUser = userService.updateUser(id,user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok("User has been deactivated");
    }
    @PutMapping("/activateAccout/{̋email}")
    public ResponseEntity<String> activateAccount( @PathVariable("̋email") String email){
        userService.activateUser(email);
        return ResponseEntity.ok("Account has been activated");

    }
    @PostMapping("/activateUser/{email}")
    public ResponseEntity<User> activateUser(@PathVariable("email")String email){
        User user = userService.activateUser(email);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email")String email) throws Exception {
        userService.resetPassword(email);
        return ResponseEntity.ok("A new password is sent to your email");
    }



}
