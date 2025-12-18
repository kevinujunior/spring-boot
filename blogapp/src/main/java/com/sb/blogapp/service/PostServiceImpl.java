package com.sb.blogapp.service;

import com.sb.blogapp.dto.PostDto;
import com.sb.blogapp.exception.ResourceNotFoundException;
import com.sb.blogapp.model.Post;
import com.sb.blogapp.model.User;
import com.sb.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    //Pass raw data or DTO in service and let Service handle the object creation

    @Override
    public Post createPost(User currentUser, PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setUser(currentUser);
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    public List<Post> getAll(User currentUser) {
        return postRepository.findAllByUserId(currentUser.getId());
    }

    @Override
    public Post updatePost(User currentUser,Long id,  PostDto postDto) {
        Post post = getPostById(id);
        if(!Objects.equals(post.getUser().getId(), currentUser.getId())){
            throw new SecurityException("Not authorized to update");
        }
        return createPost(currentUser,postDto);
    }

    @Override
    public void deletePost( User currentUser, Long id) {
        Post post = getPostById(id);
        if(!Objects.equals(post.getUser().getId(), currentUser.getId())){
            throw new SecurityException("Not authorized to update");
        }

       postRepository.delete(post);
    }



}
