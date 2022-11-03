package com.booking.hotel.service;

import com.booking.hotel.model.User;
import com.booking.hotel.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User registerUser(User user){
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setActive(true);
        return newUser;
    }

    public User register(User user) throws Exception {
        check_If_AlreadyExists(user.getEmail());
        User newUser = registerUser(user);
        userRepository.save(newUser);
        String text = "Welcome, " + newUser.getFirstName() + " Your profile has been created";
        emailService.sendEmail(newUser.getEmail(),"User Registration" ,text);
        return newUser;
    }

    public boolean emailExits(String email) {
       return userRepository.findUserByEmail(email) != null;
    }

    public boolean userNameTaken(String username) {
       return userRepository.findUsersByUserName(username).isPresent();
    }



    public User activateUser(String email){
        User user = finduserByEmail(email);
        user.setActive(true);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),"Activation Account",
                "Account has been Activated ");
        return user;
    }

    public User updateUser(String id, User user) {
        User update = userRepository.findById(id).get();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(user,update);
        userRepository.save(update);
        String text = "Hi, " + update.getFirstName() + " Your profile has been updated";
        emailService.sendEmail(user.getEmail(),"USER UPDATE" ,text);
        return user;
    }



    public void resetPassword(String email) throws Exception {
        User user = userRepository.findUserByEmail(email);
        if(user == null) throw new Exception("User not found");
        String password = RandomStringUtils.randomAlphanumeric(7);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        emailService.sendEmail(email,"Password Reset",
                "your new password is:- " + password);

    }



    public User findUserUsername(String username) throws Exception {
        return userRepository.findUsersByUserName(username).orElseThrow(
                ()-> new Exception("User is Not Found"));
    }

    public List<User> findUsersByfirstname(String name){
        return userRepository.findUsersByFirstNameStartsWith(name);
    }

    public User findUserByfullName(String firstName,String lastName){
        return userRepository.findUserByFirstNameAndLastName(firstName,lastName);
    }

    public User finduserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void deactivateUser(String id) {
        User user  = userRepository.findById(id).get();
        user.setActive(false);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Deactivation Account",
                "Account has been Deactivated");

    }

    public List<User> findActiveUsers() {
        List<User> activeUsers = new ArrayList<>();
        for(User users : getAllUsers()){
            if (users.isActive()){
                activeUsers.add(users);
            }
        }return activeUsers;
    }

    public List<User> deactivatedUsers() {
        List<User> deactivated = new ArrayList<>();
        for(User users : getAllUsers()){
            if (!users.isActive()){
                deactivated.add(users);
            }
        }return deactivated;
    }
}
