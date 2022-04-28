package com.example.demo.service;

import java.util.Collections;
import java.util.List;

import com.example.demo.models.User;
import com.example.demo.repositoriy.UserRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> list() {
        try {
            return userRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "location.country"));
        } catch (PropertyReferenceException e) {
            return Collections.emptyList();
        }
        
    }

    public void clear() {
        userRepository.deleteAll();
    }

    public void save(List<User> users) {
        userRepository.saveAll(users);
    }

}
