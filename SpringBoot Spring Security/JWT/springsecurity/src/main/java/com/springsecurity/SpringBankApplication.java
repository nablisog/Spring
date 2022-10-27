package com.springsecurity;

import com.springsecurity.model.User;
import com.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBankApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringBankApplication.class, args);
    }

}
