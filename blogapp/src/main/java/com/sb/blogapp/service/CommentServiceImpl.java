package com.sb.blogapp.service;

import com.sb.blogapp.dto.CommentDto;
import com.sb.blogapp.exception.ResourceNotFoundException;
import com.sb.blogapp.model.BlogUser;
import com.sb.blogapp.model.Comment;
import com.sb.blogapp.model.Post;
import com.sb.blogapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostServiceImpl postService;

    @Override
    public Comment addComment(BlogUser currentBlogUser, Long postId, CommentDto commentDto) {
        Comment comment = new Comment();
        Post post = postService.getPostById(currentBlogUser,postId);
        comment.setBlogUser(currentBlogUser);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setContent(commentDto.getContent());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(BlogUser currentBlogUser, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if(!Objects.equals(comment.getBlogUser().getId(), currentBlogUser.getId())){
            throw new SecurityException("Not authorized to delete");
        }

        commentRepository.delete(comment);

    }

    @Override
    public List<Comment> listByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
