package com.sb.blogapp.service;

import com.sb.blogapp.dto.CommentDto;
import com.sb.blogapp.exception.ResourceNotFoundException;
import com.sb.blogapp.model.Comment;
import com.sb.blogapp.model.Post;
import com.sb.blogapp.model.User;
import com.sb.blogapp.repository.CommentRepository;
import com.sb.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostServiceImpl postService;

    @Override
    public Comment addComment(User currentUser, Long postId, CommentDto commentDto) {
        Comment comment = new Comment();
        Post post = postService.getPostById(postId);
        comment.setUser(currentUser);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(User currentUser, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if(!Objects.equals(comment.getUser().getId(), currentUser.getId())){
            throw new SecurityException("Not authorized to update");
        }

        commentRepository.delete(comment);

    }

    @Override
    public List<Comment> listByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
