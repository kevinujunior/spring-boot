package com.sb.blogapp.controller;

import com.sb.blogapp.config.JwtUtil;
import com.sb.blogapp.dto.AuthRequest;
import com.sb.blogapp.dto.AuthResponse;
import com.sb.blogapp.model.BlogUser;
import com.sb.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


//RestController automatically serializes objects
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    //@RequestBody converts the incoming JSON login details into an AuthRequest object
    //@Validated immediately checks if those details (like username or password)
    // meet your predefined security and format rules.
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody AuthRequest req){
        try {
            BlogUser u = userService.register(req.getUsername(), req.getPassword());
            String token = jwtUtil.generateToken(u.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Registration failed: " + e.getMessage()));
        }
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody AuthRequest req) {
        try {
            BlogUser blogUser = userService.findByUsername(req.getUsername());
            if (!passwordEncoder.matches(req.getPassword(), blogUser.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
            }
            String token = jwtUtil.generateToken(blogUser.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Login failed: " + e.getMessage()));
        }
    }


}
