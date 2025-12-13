package com.sb.blogapp.service;
import com.sb.blogapp.model.User;

import java.util.Optional;

public interface UserService {

    public User register(String username, String rawPass);

    public Optional<User> findById(Long id);

    public Optional<User> findByUsername(String username);

}
