package com.grperets.library.service;

import com.grperets.library.model.User;

import java.util.List;

public interface UserService {
    User getById(Long id);
    void create(User user);
    void update(User user);
    void delete(User user);
    List<User> getAll();
}
