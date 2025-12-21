package com.sb.blogapp.service;

import com.sb.blogapp.dto.CommentDto;
import com.sb.blogapp.model.BlogUser;
import com.sb.blogapp.model.Comment;

import java.util.List;


public interface CommentService {

    public Comment addComment(BlogUser currentBlogUser, Long postId, CommentDto commentDto);
    public void deleteComment(BlogUser currentBlogUser, Long id);
    public List<Comment> listByPost(Long postId);
}
