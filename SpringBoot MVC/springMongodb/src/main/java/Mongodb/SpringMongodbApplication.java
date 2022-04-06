package Mongodb;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableMongoRepositories("Mongodb.repository")
public class SpringMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongodbApplication.class, args);
    }



}
