package com.sb.blogapp.service;
import com.sb.blogapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService  {

    public User register(String username, String rawPass);

    public User findById(Long id);

    public User findByUsername(String username);

}

