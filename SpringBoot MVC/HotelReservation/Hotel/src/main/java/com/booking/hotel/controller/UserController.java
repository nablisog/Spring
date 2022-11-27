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

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/activeUsers")
    public List<User>activeUsers(){
        return userService.findActiveUsers();
    }

    @GetMapping("/fetchUserByUserName/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable("username")String username) throws  Exception {
        User user = userService.findUserUsername(username);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user){
        if (userService.emailExits(user.getEmail())){
            return new ResponseEntity<>("Email Already Exist",HttpStatus.BAD_REQUEST);
        }
        if (userService.userNameTaken(user.getUserName())){
            return new ResponseEntity<>("Username is taken",HttpStatus.BAD_REQUEST);
        }
        userService.register(user);
        return ResponseEntity.ok("Account is Successfully created");
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody AuthRequest authRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        } catch (Exception e) {
            throw new Exception("Invalid username/password");
        }
        User user = userService.findUserUsername(authRequest.getUserName());
        if(!user.isActive()) throw new Exception("Deactivated Account");
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id")String id, @Valid @RequestBody User user) {
        if (userRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("User doesn't exit",HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(id,user);
        return new ResponseEntity<>("Account has been updated", HttpStatus.OK);
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        if (userRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("User doesn't exit",HttpStatus.BAD_REQUEST);
        }
        userService.deactivateUser(id);
        return ResponseEntity.ok("User has been deactivated");
    }

    @PostMapping("/activateAccount/{̋email}")
    public ResponseEntity<String> activateAccount( @PathVariable("̋email") String email){
        if (!userService.emailExits(email)){
            return new ResponseEntity<>("Email doesn't Exit",HttpStatus.BAD_REQUEST);
        }
        userService.activateUser(email);
        return ResponseEntity.ok("Account has been activated");

    }

    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email")String email) {
        if (!userService.emailExits(email)){
            return new ResponseEntity<>("Email doesn't Exit",HttpStatus.BAD_REQUEST);
        }
        userService.resetPassword(email);
        return ResponseEntity.ok("A new password is sent to your email");
    }



}
