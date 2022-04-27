package com.example.demo.models;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Location {
    
    private String city;
    private String state;
    private String country;
    private String postcode;

    @Embedded
    private Street street;
    @Embedded
    private Coordinates coordinates;
    @Embedded
    private Timezone timezone;

    public Location() {}

}
