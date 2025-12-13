package com.sb.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private Long id;

    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();


    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    //One user can have multiple comments
    //@aTob a lies on this side b to the referenced side
    //for example @ManyToOne (many of this entity to one of referenced entity)
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName ="id", nullable = false)
    private User user;


}
