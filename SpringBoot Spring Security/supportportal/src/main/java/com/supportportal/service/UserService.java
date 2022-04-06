package com.supportportal.service;

import com.supportportal.domain.User;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.EmailNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User register(String firstName,String lastName,String username,String email) throws EmailExistException, UsernameExistException, MessagingException;
    List<User> getUsers();
    User findByUsername(String username);
    User findUserByEmail(String email);
    User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonlocked, boolean isActive, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException;
    User updateUser(String currentUsername,String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonlocked, boolean isActive, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException;
    void deletUser(Long id);
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;
    User updateProfileImage(String username,MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException;
}
