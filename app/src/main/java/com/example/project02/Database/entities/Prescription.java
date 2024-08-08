package com.example.project02.Database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02.Database.AppDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = AppDatabase.PRESCRIPTION_TABLE)
public class Prescription {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String exercise;
    private double weight;
    private int reps;
    private int userID;

    public Prescription(String exercise, double weight, int reps, int userID) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.userID = userID;
    }

    @NonNull
    @Override
    public String toString() {
        return  exercise + '\n' +
                "Weight: " + weight + '\n' +
                "Reps: " + reps + '\n' +
                "---------------------\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription prescription = (Prescription) o;
        return id == prescription.id && Double.compare(weight, prescription.weight) == 0 && reps == prescription.reps && userID == prescription.userID && Objects.equals(exercise, prescription.exercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, weight, reps, userID);
    }
}
