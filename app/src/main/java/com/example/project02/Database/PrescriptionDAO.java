package com.example.project02.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02.Database.entities.Prescription;

import java.util.List;


@Dao
public interface PrescriptionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Prescription gymlog);

    @Query("SELECT * FROM " + PharmacyDatabase.PRESCRIPTION_TABLE )
    List<Prescription> getAllRecords();
}
