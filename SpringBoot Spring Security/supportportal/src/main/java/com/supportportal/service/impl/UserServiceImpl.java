package com.supportportal.service.impl;

import com.supportportal.domain.User;
import com.supportportal.domain.UserPrinciple;
import com.supportportal.enumeration.Role;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.EmailNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.EmailService;
import com.supportportal.service.LoginAttemptService;
import com.supportportal.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;


import static com.supportportal.constant.FileConstant.*;
import static com.supportportal.constant.UserImplConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Transactional
@Qualifier("UserDetailsService")
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private LoginAttemptService loginAttemptService;
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user ==  null){
            logger.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        }else {
            validateLoginAttempt(user);
            user.setLastLoginDate(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrinciple userPrinciple = new UserPrinciple(user);
            logger.info("Returning found User by Username : " + username);
            return userPrinciple;
        }

    }

    private void validateLoginAttempt(User user)  {
        if (user.isNotLocked()){
            if(loginAttemptService.hasExcededMaxAttempt(user.getUsername())){
                user.setNotLocked(false);
            }else{
                user.setNotLocked(true);
            }
        }else{
            loginAttemptService.evictUserFromLoginAttempt(user.getUsername());
        }
    }


    @Override
    public User register(String firstName, String lastName, String username, String email) throws EmailExistException, UsernameExistException, MessagingException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, username,email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorites());
        user.setProfileImageUrl(getTemporaryProfileImage(username));
        userRepository.save(user);
        logger.info("New User password: " + password);
        emailService.sendNewPasswordEmail(firstName,password,email);
        return user;
    }

    private String getTemporaryProfileImage(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername,String newEmail) throws UsernameExistException, EmailExistException {
        User userByNewusername = findByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);

        if(StringUtils.isNotBlank(currentUsername)){
            //FOR UPDATING Users
            User currentUser = findByUsername(currentUsername);
            if(currentUser == null){
                throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if(userByNewusername != null && !userByNewusername.getId().equals(currentUser.getId())){
                throw new UsernameExistException(USER_NAME_ALREADY_EXITS);
            }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())){
                throw new EmailExistException(EMAIL_ALREADY_EXITS);
            }
            return currentUser;
        }
        else{
            //FOR NEW User
            if(userByNewusername != null ){
                throw new UsernameExistException(USER_NAME_ALREADY_EXITS);
            }
            if(userByNewEmail != null){
                throw new EmailExistException(EMAIL_ALREADY_EXITS);
            }
            return null;

        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonlocked, boolean isActive, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException {
        validateNewUsernameAndEmail(StringUtils.EMPTY,username,email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(encodePassword(password));
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorites());
        user.setProfileImageUrl(getTemporaryProfileImage(username));
        userRepository.save(user);
        saveProfileImg(user, profileImage);

        return user;
    }

    private void saveProfileImg(User user, MultipartFile profileImage) throws IOException {
        if (profileImage != null){
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
                logger.info(DIRECTORY_CREATED + userFolder);
            }
            Files.delete(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(),
                    userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), StandardCopyOption.REPLACE_EXISTING);

            user.setProfileImageUrl(setProfileImgUrl(user.getUsername()));
            userRepository.save(user);
            logger.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String setProfileImgUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(
                USER_IMAGE_PATH + username + username + FORWARD_SLASH + username + DOT + JPG_EXTENSION).toUriString();
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonlocked, boolean isActive, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException {
        User currentUser = validateNewUsernameAndEmail(currentUsername,newUsername,newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonlocked);
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorites());
        userRepository.save(currentUser);
        saveProfileImg(currentUser, profileImage);

        return currentUser;
    }

    @Override
    public void deletUser(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new EmailNotFoundException("Email is not found");
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user.getFirstName(),password,user.getEmail());

    }

    @Override
    public User updateProfileImage(String username, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException {
        User user = validateNewUsernameAndEmail(username,null,null);
        saveProfileImg(user,profileImage);
        return user;
    }
}
