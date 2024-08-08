package com.example.project02.Database.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


import com.example.project02.Database.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.PATIENT_TABLE, indices = {@Index(value = {"username"}, unique = true)})
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    public Patient(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id && isAdmin == patient.isAdmin && Objects.equals(username, patient.username) && Objects.equals(password, patient.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
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
        return isAdmin;
    }

    public void setAddmin(boolean addmin) {
        isAdmin = addmin;
    }
}
