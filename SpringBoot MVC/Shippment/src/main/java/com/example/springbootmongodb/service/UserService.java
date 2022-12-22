package com.example.springbootmongodb.service;

import com.example.springbootmongodb.entity.User;
import com.example.springbootmongodb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean emailExits(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public void register(User user) {
        userRepository.save(user);
    }

    public void updateUser(String id, User user) {
        User update = userRepository.findById(id).get();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(user,update);
        userRepository.save(update);
    }

    public void deactivateUser(String id) {
        userRepository.deleteById(id);
    }


}
