package com.example.demo.service;

import java.util.List;

import com.example.demo.models.User;
import com.example.demo.repositoriy.UserRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> list() {
        return userRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "location.country"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void save(List<User> users) {
        userRepository.saveAll(users);
    }

}
