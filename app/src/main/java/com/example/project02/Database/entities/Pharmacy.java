package com.example.project02.Database.entities;

import static com.example.project02.Database.PharmacyDatabase.PHARMACY_TABLE;
import com.example.project02.Database.typeConverters.Converters;

import androidx.room.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity(tableName = PHARMACY_TABLE)
@TypeConverters(Converters.class)
public class Pharmacy {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String name;
    private LocalDateTime date;
    private String address;
    private String phone;
    private List<DrugCost> drugCosts;

    public Pharmacy(String name,int userId, String address, String phone, List<DrugCost> drugCosts) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.drugCosts = drugCosts;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    public Pharmacy() {
        this.drugCosts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<DrugCost> getDrugCosts() {
        return drugCosts;
    }

    public void setDrugCosts(List<DrugCost> drugCosts) {
        this.drugCosts = drugCosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return id == pharmacy.id &&
                Objects.equals(name, pharmacy.name) &&
                Objects.equals(address, pharmacy.address) &&
                Objects.equals(phone, pharmacy.phone) &&
                Objects.equals(drugCosts, pharmacy.drugCosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone, drugCosts);
    }

    public static class DrugCost {
        private String drugName;
        private double cost;

        public DrugCost(String drugName, double cost) {
            this.drugName = drugName;
            this.cost = cost;
        }

        public String getDrugName() {
            return drugName;
        }

        public void setDrugName(String drugName) {
            this.drugName = drugName;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

    }
}