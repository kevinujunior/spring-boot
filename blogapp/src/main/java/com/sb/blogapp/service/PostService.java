package com.sb.blogapp.service;

import com.sb.blogapp.dto.PostDto;
import com.sb.blogapp.model.Post;
import com.sb.blogapp.model.User;

import java.util.List;

public interface PostService {

    public Post createPost(User currentUSer, PostDto postDto);
    public Post getPostById(Long id);
    public List<Post> getAll(User currentUser);
    public Post updatePost(User currentUser,Long id,  PostDto postDto);
    public void deletePost(User currentUser, Long id);
}
