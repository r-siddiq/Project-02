package com.example.project02.Database.entities;

import androidx.room.Entity;

@Entity
public class Patient {
    private int id;
    private String ssn;
    private String lastName;
    private String firstName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String birthDate;  // maybe use date object?
}
