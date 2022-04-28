package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class User {
    
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private String country;
    private String name;
    private String gender;
    private String email;

    public User() {};

}
