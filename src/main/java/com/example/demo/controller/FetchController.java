package com.example.demo.controller;


import java.util.List;

import com.example.demo.models.User;
import com.example.demo.models.Wrapper;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class FetchController {

    private UserService userService;

    @Value("${randomuser.url}")
    private  String BASE_URL;
    @Value("${randomuser.userSize}")
    private String USERSIZE;

    public FetchController(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRateString = "#{${randomuser.period} * 1000}")
    @GetMapping("/update")
    public void getUsers() {
        String url = BASE_URL + "&results=" + USERSIZE;

        RestTemplate restTemplate = new RestTemplate();

        Wrapper wrapper = restTemplate.getForObject(url, Wrapper.class);

        for (User user : wrapper.getResults()) {
            System.out.println(user.toString());
        }
        this.userService.save(wrapper.getResults());
        
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> user = (List<User>) this.userService.list();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
