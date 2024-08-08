package com.example.project02.Database.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDateTime;
import java.util.Objects;

import com.example.project02.Database.database.PharmacyDatabase;

@Entity(tableName = DRUG_TABLE);
public class Drug {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Drug(int id, String name) {
        this.id = id;
        this.name = name;
    }
}