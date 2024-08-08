package com.example.project02.Database.database.entities;

import static com.example.project02.Database.database.PharmacyDatabase.PATIENT_TABLE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity(tableName = PATIENT_TABLE)
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
    private LocalDate birthDate;  // maybe use date object?

    /**
     * Constructs a new Patient with the specified details.
     * @param ssn the Social Security Number of the patient
     * @param lastName the last name of the patient
     * @param firstName the first name of the patient
     * @param street the street address of the patient
     * @param city the city of the patient's address
     * @param state the state of the patient's address
     * @param zipCode the zip code of the patient's address
     * @param birthDate the birth date of the patient
     */
    public Patient(@NonNull String ssn, @NonNull String lastName, @NonNull String firstName,
                   @NonNull String street, @NonNull String city, @NonNull String state,
                   @NonNull String zipCode, @NonNull LocalDate birthDate) {
        this.ssn = ssn;
        this.lastName = lastName;
        this.firstName = firstName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.birthDate = birthDate;
    }

    /**
     * Default constructor required by Room.
     */
    public Patient() {
        // Default constructor for Room
    }

    /**
     * Returns the ID of the Patient.
     * @return the ID of the Patient
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the Patient.
     * @param id the ID to set for the Patient
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Social Security Number of the Patient.
     * @return the SSN of the Patient
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the Social Security Number of the Patient.
     * @param ssn the SSN to set for the Patient
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Returns the last name of the Patient.
     * @return the last name of the Patient
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the Patient.
     * @param lastName the last name to set for the Patient
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the first name of the Patient.
     * @return the first name of the Patient
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the Patient.
     * @param firstName the first name to set for the Patient
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the street address of the Patient.
     * @return the street address of the Patient
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address of the Patient.
     * @param street the street address to set for the Patient
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns the city of the Patient's address.
     * @return the city of the Patient's address
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the Patient's address.
     * @param city the city to set for the Patient's address
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the state of the Patient's address.
     * @return the state of the Patient's address
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the Patient's address.
     * @param state the state to set for the Patient's address
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the zip code of the Patient's address.
     * @return the zip code of the Patient's address
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the zip code of the Patient's address.
     * @param zipCode the zip code to set for the Patient's address
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Returns the birth date of the Patient.
     * @return the birth date of the Patient
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of the Patient.
     * @param birthDate the birth date to set for the Patient
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Compares this Patient to the specified object.
     * @param o the object to compare this Patient against
     * @return true if the given object represents a Patient equivalent to this Patient, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                Objects.equals(ssn, patient.ssn) &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(street, patient.street) &&
                Objects.equals(city, patient.city) &&
                Objects.equals(state, patient.state) &&
                Objects.equals(zipCode, patient.zipCode) &&
                Objects.equals(birthDate, patient.birthDate);
    }

    /**
     * Returns a hash code value for the Patient. This method is supported for the benefit of
     * hash tables such as those provided by HashMap.
     * @return a hash code value for this Patient
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, ssn, lastName, firstName, street, city, state, zipCode, birthDate);
    }
}