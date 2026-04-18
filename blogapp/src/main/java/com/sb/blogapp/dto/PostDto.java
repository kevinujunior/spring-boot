package com.sb.blogapp.dto;

import lombok.*;
import jakarta.validation.constraints.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PostDto {
    private Long id;
    @NotBlank private String title;
    @NotBlank private String content;
}


