package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Street {

    private String number;
    @Column( name = "street_name")
    private String name;

    public Street() {}

}
