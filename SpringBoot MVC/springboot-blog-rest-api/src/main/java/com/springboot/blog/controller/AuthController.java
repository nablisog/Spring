package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignupDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepositroy;
import com.springboot.blog.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
@Api(value = "Auto controller exposes Signin and Signup Rest APIs")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepositroy userRepositroy;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "REST API to Register or SignUp user to Blog app")
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
    Authentication authentication =  authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken
                        (loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token from tokenprovider
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @ApiOperation(value = "REST API to Signin or SignUp user to Blog app")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signUpDto){

        // add check for username exists in a DB
        if(userRepositroy.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepositroy.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepositroy.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }


}
