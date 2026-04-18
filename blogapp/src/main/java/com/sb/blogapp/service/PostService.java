package com.sb.blogapp.service;

import com.sb.blogapp.dto.PostDto;
import com.sb.blogapp.model.Post;
import com.sb.blogapp.model.BlogUser;

import java.util.List;


public interface PostService {

    public Post createPost(BlogUser currentUser, PostDto postDto);
    public Post getPostById(BlogUser currentUser,Long id);
    public List<Post> getAll(BlogUser currentBlogUser);
    public Post updatePost(BlogUser currentBlogUser, Long id, PostDto postDto);
    public void deletePost(BlogUser currentBlogUser, Long id);
}
