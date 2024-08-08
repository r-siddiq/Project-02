package com.example.project02.Database.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Objects;

@Entity
public class Pharmacy {
    @PrimaryKey(autoGenerate = true)

    private String pharmName;
    private String pharmCity;
    private String pharmStreet;
    private String pharmState;
    private String pharmZipCode;
    private ArrayList<String> inventory; //Inventory of medication?
    private ArrayList<Integer> pharmHours; //hours of operation
    private ArrayList<String> pharmDays; //Days open

    public String getPharmName() {
        return pharmName;
    }

    public void setPharmName(String pharmName) {
        this.pharmName = pharmName;
    }

    public String getPharmCity() {
        return pharmCity;
    }

    public void setPharmCity(String pharmCity) {
        this.pharmCity = pharmCity;
    }

    public String getPharmStreet() {
        return pharmStreet;
    }

    public void setPharmStreet(String pharmStreet) {
        this.pharmStreet = pharmStreet;
    }

    public String getPharmState() {
        return pharmState;
    }

    public void setPharmState(String pharmState) {
        this.pharmState = pharmState;
    }

    public String getPharmZipCode() {
        return pharmZipCode;
    }

    public void setPharmZipCode(String pharmZipCode) {
        this.pharmZipCode = pharmZipCode;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Integer> getPharmHours() {
        return pharmHours;
    }

    public void setPharmHours(ArrayList<Integer> pharmHours) {
        this.pharmHours = pharmHours;
    }

    public ArrayList<String> getPharmDats() {
        return pharmDays;
    }

    public void setPharmDats(ArrayList<String> pharmDats) {
        this.pharmDays = pharmDats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(pharmName, pharmacy.pharmName) && Objects.equals(pharmCity, pharmacy.pharmCity) && Objects.equals(pharmStreet, pharmacy.pharmStreet) && Objects.equals(pharmState, pharmacy.pharmState) && Objects.equals(pharmZipCode, pharmacy.pharmZipCode) && Objects.equals(pharmHours, pharmacy.pharmHours) && Objects.equals(pharmDays, pharmacy.pharmDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pharmName, pharmCity, pharmStreet, pharmState, pharmZipCode, pharmHours, pharmDays);
    }

    public Pharmacy(String pharmName, String pharmCity, String pharmStreet, String pharmState, String pharmZipCode, ArrayList<String> inventory, ArrayList<Integer> pharmHours, ArrayList<String> pharmDats) {
        this.pharmName = pharmName;
        this.pharmCity = pharmCity;
        this.pharmStreet = pharmStreet;
        this.pharmState = pharmState;
        this.pharmZipCode = pharmZipCode;
        this.pharmHours = pharmHours;
        this.pharmDays = pharmDats;
        inventory = new ArrayList<String>();
    }
}
