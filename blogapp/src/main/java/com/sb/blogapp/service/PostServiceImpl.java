package com.sb.blogapp.service;

import com.sb.blogapp.dto.PostDto;
import com.sb.blogapp.exception.ResourceNotFoundException;
import com.sb.blogapp.model.Post;
import com.sb.blogapp.model.BlogUser;
import com.sb.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    //Pass raw data or DTO in service and let Service handle the object creation

    @Override
    public Post createPost(BlogUser currentBlogUser, PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setBlogUser(currentBlogUser);
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(BlogUser blogUser, Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    public List<Post> getAll(BlogUser currentBlogUser) {
        return postRepository.findAllByBlogUserId(currentBlogUser.getId());
    }

    @Override
    public Post updatePost(BlogUser currentBlogUser, Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if(!Objects.equals(post.getBlogUser().getId(), currentBlogUser.getId())){
            throw new SecurityException("Not authorized to update");
        }
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    PostDto postDto = new PostDto();

    @Override
    public void deletePost(BlogUser currentBlogUser, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));;
        if(!Objects.equals(post.getBlogUser().getId(), currentBlogUser.getId())){
            throw new SecurityException("Not authorized to update");
        }

       postRepository.delete(post);
    }



}
