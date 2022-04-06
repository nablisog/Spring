package com.dailycodebuffer.cloud.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/UserServiceFallBack")
    public String UserServiceFallBackMethod(){
        return "User Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/DepartmentServiceFallBack")
    public String DepartmentServiceFallBackMethod(){
        return "Department Service is taking longer than Expected." +
                " Please try again later";
    }


}
