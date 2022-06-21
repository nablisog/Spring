package com.expensetrackerapi.service;

import com.expensetrackerapi.entity.User;
import com.expensetrackerapi.entity.UserModel;

public interface UserService {
    User createUser(UserModel user);
    User readUser();
    User updateUser(UserModel user);
    void deleteUser();
    User getLoggedInUser();
}
