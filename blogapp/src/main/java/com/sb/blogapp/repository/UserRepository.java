package com.sb.blogapp.repository;

import com.sb.blogapp.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BlogUser,Long> {

    Optional<BlogUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
