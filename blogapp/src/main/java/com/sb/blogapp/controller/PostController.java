package com.sb.blogapp.controller;


import com.sb.blogapp.dto.PostDto;
import com.sb.blogapp.model.BlogUser;
import com.sb.blogapp.model.Post;
import com.sb.blogapp.service.PostService;
import com.sb.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;


    //@AuthenticationPrincipalInstead of manually fetching the user from the SecurityContextHolder, this annotation tells Spring to
    // look at the current authentication and inject the principal
    @PostMapping
    public ResponseEntity<?> createPost(@Validated @RequestBody PostDto postDto,
                           @AuthenticationPrincipal UserDetails principal){

        try {
            BlogUser blogUser = userService.findByUsername(principal.getUsername());
            Post saved = postService.createPost(blogUser, postDto);
            return ResponseEntity.ok(toDto(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Post creation failed: " + e.getMessage()));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetails principal) {
        try {
            BlogUser blogUser = userService.findByUsername(principal.getUsername());
            return ResponseEntity.ok(postService.getPostById(blogUser,id));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Post get failed: " + e.getMessage()));
        }
    }


    @GetMapping
    public ResponseEntity<?> list(@AuthenticationPrincipal UserDetails principal) {
        try {
            BlogUser blogUser = userService.findByUsername(principal.getUsername());
            return ResponseEntity.ok(postService.getAll(blogUser));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Post get failed: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Validated @RequestBody PostDto postDto,
                                    @AuthenticationPrincipal UserDetails principal) {

        //BlogUser currentBlogUser, Long id, PostDto postDto
        try {
            BlogUser blogUser = userService.findByUsername(principal.getUsername());
            Post p = postService.updatePost(blogUser, id, postDto);
            return ResponseEntity.ok(toDto(p));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Post update failed: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails principal) {
        try {
            BlogUser blogUser = userService.findByUsername(principal.getUsername());
            postService.deletePost(blogUser, id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Post delete failed: " + e.getMessage()));
        }
    }




    private PostDto toDto(Post p) {
        PostDto dto = new PostDto();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setContent(p.getContent());
        return dto;
    }
}
