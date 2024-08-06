package com.example.project02.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02.Database.entities.Patient;

import java.util.List;

@Dao
public interface PatientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient... patients);

    @Delete
    void delete(Patient user);

    @Query("SELECT * from " + AppDatabase.PATIENT_TABLE + " ORDER BY username")
    LiveData<List<Patient>> getAllUsers();

    @Query("DELETE from " + AppDatabase.PATIENT_TABLE)
    void deleteAll();

    @Query("SELECT * from " + AppDatabase.PATIENT_TABLE + " WHERE username == :username" )
    LiveData<Patient> getPatientByUsername(String username);

    @Query("SELECT * from " + AppDatabase.PATIENT_TABLE + " WHERE id == :userId" )
    LiveData<Patient> getPatientByUserId(int userId);
}
