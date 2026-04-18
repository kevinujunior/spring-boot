package com.sb.blogapp.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank private String content;
}