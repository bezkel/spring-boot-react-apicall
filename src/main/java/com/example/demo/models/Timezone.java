package com.example.demo.models;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Timezone {
    
    @Column( name = "timezone_offset")
    String offset;
    @Column( name = "timezone_description")
    String description;

    public Timezone() {}

}
