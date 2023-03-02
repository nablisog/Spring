package com.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home-controller")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> greeting(){
        return ResponseEntity.ok("Greeting!!! Welcome to this page");
    }
}
