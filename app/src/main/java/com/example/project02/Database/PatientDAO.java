package com.example.project02.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.project02.Database.entities.Patient;


@Dao
public interface PatientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient patient);
}
