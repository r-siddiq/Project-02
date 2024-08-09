package com.example.project02.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02.Database.entities.Prescription;
import com.example.project02.Database.entities.User;

import java.util.List;


@Dao
public interface PrescriptionDAO {

    @Query("DELETE FROM " + PharmacyDatabase.PRESCRIPTION_TABLE)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Prescription prescription);

    @Query("SELECT * FROM " + PharmacyDatabase.PRESCRIPTION_TABLE )
    List<Prescription> getAllRecords();

    @Update
    void update(Prescription prescription);

    @Delete
    void delete(Prescription prescription);

    @Query("SELECT * FROM " + PharmacyDatabase.PRESCRIPTION_TABLE + " WHERE drugName = :drugName")
    LiveData<Prescription> getPrescriptionByDrugName(String drugName);

    @Query("SELECT * FROM " + PharmacyDatabase.PRESCRIPTION_TABLE + " ORDER BY drugName")
    LiveData<List<Prescription>> getAllPrescriptions();
}
