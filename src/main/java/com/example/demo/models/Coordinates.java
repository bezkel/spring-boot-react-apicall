package com.example.demo.models;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Coordinates {
    
    private String latitude;
    private String longitude;

    public Coordinates() {}

}
