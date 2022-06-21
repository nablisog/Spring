package com.expensetrackerapi.service;

import com.expensetrackerapi.entity.User;
import com.expensetrackerapi.entity.UserModel;
import com.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.expensetrackerapi.exception.ResourceNotFoundException;
import com.expensetrackerapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserModel userModel) {
        if (userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemAlreadyExistsException("User is already register with this email " +
                    userModel.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(userModel,newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User readUser() throws ResourceNotFoundException {
        Long id = getLoggedInUser().getId();
        return userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("user not found for the id " + id));
    }

    @Override
    public User updateUser(UserModel user) {
        User updateUser = readUser();
        updateUser.setName(user.getName() != null ? user.getName() : updateUser.getName());
        updateUser.setEmail(user.getEmail() != null ? user.getEmail() : updateUser.getEmail());
        updateUser.setPassword(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : updateUser.getPassword());
        updateUser.setAge(user.getAge() != null ? user.getAge() : updateUser.getAge());

        return userRepository.save(updateUser);
    }

    @Override
    public void deleteUser() {
        User user = readUser();
        userRepository.delete(user);

    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return  userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found for the email " + email));
    }
}
