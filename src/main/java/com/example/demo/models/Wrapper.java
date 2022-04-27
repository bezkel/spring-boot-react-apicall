package com.example.demo.models;

import java.util.List;

import lombok.Data;

@Data
public class Wrapper {
    private List<User> results;

    public Wrapper() {}

}
