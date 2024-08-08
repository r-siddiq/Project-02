package com.example.project02.Database.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Patient {

    @PrimaryKey(autoGenerate = true)
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
