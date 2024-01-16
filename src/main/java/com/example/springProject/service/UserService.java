package com.example.springProject.service;

import com.example.springProject.dto.UserDto;
import com.example.springProject.model.RegistrationRequest;
import com.example.springProject.model.User;

import java.util.List;

public interface UserService {

    boolean checkEmail(String email);

    UserDto registerUser(RegistrationRequest registrationRequest);

    UserDto getLoginUser();

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();

    UserDto createUser(User user);

    UserDto updateUser(User user);

    void deleteUser(User user);

    Integer getUserByUsername(String username);
    List<User> findAllUsers();
}