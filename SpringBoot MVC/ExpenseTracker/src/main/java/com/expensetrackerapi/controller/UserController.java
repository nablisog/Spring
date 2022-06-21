package com.expensetrackerapi.controller;

import com.expensetrackerapi.entity.User;
import com.expensetrackerapi.entity.UserModel;
import com.expensetrackerapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@AllArgsConstructor

public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> readUser(){
        User user = userService.readUser();
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @PutMapping("/updateProfile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel updatedUser){
        User user = userService.updateUser(updatedUser);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUserById(){
        userService.deleteUser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
