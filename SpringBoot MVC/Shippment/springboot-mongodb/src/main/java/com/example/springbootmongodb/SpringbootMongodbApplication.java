package com.example.springbootmongodb;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Scanner;

@SpringBootApplication
@EnableSwagger2

public class SpringbootMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMongodbApplication.class, args);
/*
        String password = RandomStringUtils.randomAlphanumeric(7);
        System.out.println(password);
 */


    }


}
