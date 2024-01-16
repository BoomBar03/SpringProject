package com.example.springProject.service;

import com.example.springProject.config.MySecurityAuthentication;
import com.example.springProject.config.MySecurityUser;
import com.example.springProject.dto.UserDto;
import com.example.springProject.mapper.RoleMapper;
import com.example.springProject.mapper.UserMapper;
import com.example.springProject.model.RegistrationRequest;
import com.example.springProject.model.User;
import com.example.springProject.repository.RoleRepository;
import com.example.springProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmailAddress(email);
    }

    @Override
    public UserDto registerUser(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .password(registrationRequest.getPassword())
                .emailAddress(registrationRequest.getEmailAddress())
                .role((roleRepository.findByRole("USER")))
                .build();




        return this.createUser(user);
    }

    public UserDto getLoginUser(){
        return userMapper.userEntityToDto(userRepository.findLoginUser().orElse(null));
    }

    public UserDto getUserById(Integer id){
        return userMapper.userEntityToDto(userRepository.findById(id).orElse(null));
    }

    public List<UserDto> getAllUsers(){
        return userMapper.userListEntityToDto(userRepository.findAll());
    }

    public UserDto createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.userEntityToDto(userRepository.save(user));
    }

    public UserDto updateUser(User user){
        return userMapper.userEntityToDto(userRepository.save(user));
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public Integer getUserByUsername(String username){
        List<User> users = userRepository.findAll();
        for(User u: users){
            if(u.getUsername().equals(username))
                return u.getId();
        }
        return null;
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

}