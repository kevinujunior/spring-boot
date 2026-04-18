package com.sb.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "Username is required and cannot be empty")
    private String username;


    @NotBlank(message = "Password is required")
    private String password;
}
