package com.grperets.library.service.impl;

import com.grperets.library.model.User;
import com.grperets.library.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
