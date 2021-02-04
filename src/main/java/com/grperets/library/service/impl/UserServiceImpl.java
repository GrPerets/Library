package com.grperets.library.service.impl;

import com.grperets.library.model.Status;
import com.grperets.library.model.User;
import com.grperets.library.repository.UserRepository;
import com.grperets.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getById(Long id) {
        log.info("IN UserServiceImpl getById id: {}", id);
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setStatus(Status.ACTIVE);
        log.info("IN UserServiceImpl create user: {}", user);
        this.userRepository.save(user);
    }

    @Override
    public void update(User user) {
        User existingUser = this.userRepository.findById(user.getId()).orElse(new User());
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setUpdated(Timestamp.valueOf(LocalDateTime.now()));

        log.info("IN UserServiceImpl update user: {}", user);
        this.userRepository.save(existingUser);
    }

    @Override
    public void delete(User user) {
        log.info("IN UserServiceImpl delete user: {}", user);
        this.userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        log.info("IN UserServiceImpl getAll");
        return this.userRepository.findAll();
    }


}
