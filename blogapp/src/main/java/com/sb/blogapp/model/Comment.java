package com.sb.blogapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private Long id;

    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();


    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Post post;

    //One user can have multiple comments
    //@aTob a lies on this side b to the referenced side
    //for example @ManyToOne (many of this entity to one of referenced entity)
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName ="id", nullable = false)
    @JsonIgnoreProperties({"posts", "comments"})
    private BlogUser blogUser;


}
