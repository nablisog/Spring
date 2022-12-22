package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findUserByEmail(String email);

}
