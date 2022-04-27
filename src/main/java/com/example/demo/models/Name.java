package com.example.demo.models;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Name {
   
    private String title;
    private String first;
    private String last;

    public Name() {}

}
