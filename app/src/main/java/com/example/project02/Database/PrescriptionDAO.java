package com.example.project02.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02.Database.entities.Prescription;

import java.util.List;


@Dao
public interface PrescriptionDAO {

    @Query("DELETE FROM " + PharmacyDatabase.PRESCRIPTION_TABLE)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Prescription prescription);

    @Query("SELECT * FROM " + PharmacyDatabase.PRESCRIPTION_TABLE )
    List<Prescription> getAllRecords();
}
