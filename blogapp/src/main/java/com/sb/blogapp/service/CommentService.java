package com.sb.blogapp.service;

import com.sb.blogapp.dto.CommentDto;
import com.sb.blogapp.model.Comment;
import com.sb.blogapp.model.User;

import java.util.List;


public interface CommentService {

    public Comment addComment(User currentUser, Long postId, CommentDto commentDto);
    public void deleteComment(User currentUser, Long id);
    public List<Comment> listByPost(Long postId);
}
