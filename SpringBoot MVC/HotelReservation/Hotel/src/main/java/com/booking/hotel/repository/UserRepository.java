package com.booking.hotel.repository;

import com.booking.hotel.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User>findUsersByUserName(String name);
    User findUserByEmail(String email);
    User findUserByUserNameAndPassword(String userName, String password);
    List<User> findUsersByFirstNameStartsWith(String name);
    User findUserByFirstNameAndLastName(String firstname,String lastname);


}
