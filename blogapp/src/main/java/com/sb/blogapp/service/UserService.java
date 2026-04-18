package com.sb.blogapp.service;
import com.sb.blogapp.model.BlogUser;

public interface UserService  {

    public BlogUser register(String username, String rawPass);

    public BlogUser findById(Long id);

    public BlogUser findByUsername(String username);

}

