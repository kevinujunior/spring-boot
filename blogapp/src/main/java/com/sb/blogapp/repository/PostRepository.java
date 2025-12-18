package com.sb.blogapp.repository;

import com.sb.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByUserId(Long userId);
}
