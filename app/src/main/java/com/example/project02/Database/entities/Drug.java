package com.example.project02.Database.entities;

import static com.example.project02.Database.PharmacyDatabase.DRUG_TABLE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Objects;

/**
 * Represents a Drug entity in the Pharmacy application.
 */
@Entity(tableName = DRUG_TABLE)
public class Drug {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    /**
     * Constructs a new Drug with the specified name. The ID will be auto-generated.
     * @param name the name of the drug
     */
    public Drug(@NonNull String name) {
        this.name = name;
    }

    /**
     * Default constructor required by Room.
     */
    public Drug() {
        // Default constructor for Room
    }

    /**
     * Returns the ID of the Drug.
     * @return the ID of the Drug
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the Drug.
     * @param id the ID to set for the Drug
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the Drug.
     * @return the name of the Drug
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Drug.
     * @param name the name to set for the Drug
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compares this Drug to the specified object.
     * @param o the object to compare this Drug against
     * @return true if the given object represents a Drug equivalent to this Drug, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return id == drug.id && Objects.equals(name, drug.name);
    }

    /**
     * Returns a hash code value for the Drug. This method is supported for the benefit of
     * hash tables such as those provided by HashMap.
     * @return a hash code value for this Drug
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}