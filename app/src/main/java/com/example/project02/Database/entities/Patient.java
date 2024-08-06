package com.example.project02.Database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02.Database.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.PATIENT_TABLE)
public class Patient {

    @PrimaryKey(autoGenerate = true)

    private int id;
    private String username;
    private String password;
    private boolean isAddmin;

    public Patient(String username, String password) {
        this.username = username;
        this.password = password;
        isAddmin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAddmin() {
        return isAddmin;
    }

    public void setAddmin(boolean addmin) {
        isAddmin = addmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id && isAddmin == patient.isAddmin && Objects.equals(username, patient.username) && Objects.equals(password, patient.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAddmin);
    }
}