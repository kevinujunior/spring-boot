package com.sb.blogapp.controller;


import com.sb.blogapp.dto.CommentDto;
import com.sb.blogapp.model.BlogUser;
import com.sb.blogapp.model.Comment;
import com.sb.blogapp.service.CommentService;
import com.sb.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;


    @GetMapping("/{postId}")
    public ResponseEntity<?> list(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(commentService.listByPost(postId));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Comment list failed: " + e.getMessage()));
        }

    }


    @PostMapping("/{postId}")
    public ResponseEntity<?> add(@PathVariable Long postId,
                                          @Validated @RequestBody CommentDto commentDto,
                                          @AuthenticationPrincipal UserDetails principal) {

        try {
            BlogUser blogUser = userService.findByUsername(principal.getUsername());
            Comment saved = commentService.addComment(blogUser, postId, commentDto);
            return ResponseEntity.ok(toDto(saved));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Comment add failed: " + e.getMessage()));
        }
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails principal) {
        try {
            BlogUser blogUser = userService.findByUsername(principal.getUsername());
            commentService.deleteComment(blogUser, commentId);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Comment delete failed: " + e.getMessage()));
        }
    }

    private CommentDto toDto(Comment c) {
        CommentDto dto = new CommentDto();
        dto.setId(c.getId());
        dto.setContent(c.getContent());
        return dto;
    }

}
