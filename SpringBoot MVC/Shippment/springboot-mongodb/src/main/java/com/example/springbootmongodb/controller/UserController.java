package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.entity.User;
import com.example.springbootmongodb.repository.UserRepository;
import com.example.springbootmongodb.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user){
        if (userService.emailExits(user.getEmail())){
            return new ResponseEntity<>("Email Already Exist",HttpStatus.BAD_REQUEST);
        }
        userService.register(user);
        return ResponseEntity.ok("Account is Successfully created");
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
        return ResponseEntity.ok("User has been deleted");
    }
}
