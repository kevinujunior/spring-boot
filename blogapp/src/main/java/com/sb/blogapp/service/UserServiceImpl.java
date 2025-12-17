package com.sb.blogapp.service;

import com.sb.blogapp.exception.ResourceNotFoundException;
import com.sb.blogapp.model.User;
import com.sb.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService,UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User register(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) throw new IllegalArgumentException("Username already exists");
        User u = new User(username,passwordEncoder.encode(rawPassword));
        return userRepository.save(u);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("user not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Custom Implementation");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return a Spring Security 'UserDetails' object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Should be already encoded
                .authorities(Collections.emptyList()) // Add roles/authorities here
                .build();
    }
}
