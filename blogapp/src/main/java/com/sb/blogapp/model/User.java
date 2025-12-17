package com.sb.blogapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;

@Data
@Entity
public class User{


    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 8;


    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private Long id;

    @NotBlank(message = "Please enter the username")
    @Length(min = MIN_USERNAME_LENGTH, message = "Username must be at least " + MIN_USERNAME_LENGTH + " characters long")
    @Column(unique = true,nullable = false)
    private String username;


    @JsonIgnore
    @Length(min = MIN_PASSWORD_LENGTH, message = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters long")
    @NotBlank(message = "Please enter the password")
    @Column(nullable = false)
    private String password;


    //one-to-many relation
    //one user to many posts
    //first part of relation is used for current entity
    //cascade means deletion type here all means if a user is deleted all posts will be deleted
    //orphanRemoval -> parent doesn't exist child will be automatically removed
    //mappedBy is the inverse side of fk which means fk doesn't lie in this entity
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();


}
