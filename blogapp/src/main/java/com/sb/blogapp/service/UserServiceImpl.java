package com.sb.blogapp.service;

import com.sb.blogapp.exception.ResourceNotFoundException;
import com.sb.blogapp.model.User;
import com.sb.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    public User register(String username, String rawPass) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found"));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("user not found"));
    }


}
