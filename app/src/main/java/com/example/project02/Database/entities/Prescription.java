package com.example.project02.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Prescription {

    @PrimaryKey(autoGenerate = true)

    private String medicationName;
    private Integer medicationCount;
    private Integer unitSize;
    private String medicationStrength; //mg, g, etc.
    private Integer medicationRefillTimes;
    private LocalDate medicationRefillDate;
    private String medicationInstructions;

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Integer getMedicationCount() {
        return medicationCount;
    }

    public void setMedicationCount(Integer medicationCount) {
        this.medicationCount = medicationCount;
    }

    public Integer getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(Integer unitSize) {
        this.unitSize = unitSize;
    }

    public String getMedicationStrength() {
        return medicationStrength;
    }

    public void setMedicationStrength(String medicationStrength) {
        this.medicationStrength = medicationStrength;
    }

    public Integer getMedicationRefillTimes() {
        return medicationRefillTimes;
    }

    public void setMedicationRefillTimes(Integer medicationRefillTimes) {
        this.medicationRefillTimes = medicationRefillTimes;
    }

    public LocalDate getMedicationRefillDate() {
        return medicationRefillDate;
    }

    public void setMedicationRefillDate(LocalDate medicationRefillDate) {
        this.medicationRefillDate = medicationRefillDate;
    }

    public String getMedicationInstructions() {
        return medicationInstructions;
    }

    public void setMedicationInstructions(String medicationInstructions) {
        this.medicationInstructions = medicationInstructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return Objects.equals(medicationName, that.medicationName) && Objects.equals(medicationCount, that.medicationCount) && Objects.equals(unitSize, that.unitSize) && Objects.equals(medicationStrength, that.medicationStrength) && Objects.equals(medicationRefillTimes, that.medicationRefillTimes) && Objects.equals(medicationInstructions, that.medicationInstructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicationName, medicationCount, unitSize, medicationStrength, medicationRefillTimes, medicationInstructions);
    }

    public Prescription(String medicationName, Integer medicationCount, Integer unitSize, String medicationStrength, Integer medicationRefillTimes, String medicationInstructions) {
        this.medicationName = medicationName;
        this.medicationCount = medicationCount;
        this.unitSize = unitSize;
        this.medicationStrength = medicationStrength;
        this.medicationRefillTimes = medicationRefillTimes;
        this.medicationInstructions = medicationInstructions;
    }
}
