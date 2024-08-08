package com.example.project02.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02.Database.entities.Pharmacy;

import java.util.List;

@Dao
public interface PharmacyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pharmacy pharmacy);

    @Query("SELECT * FROM " + PharmacyDatabase.PHARMACY_TABLE + " ORDER BY date DESC")
    List<Pharmacy> getAllRecords();

    @Query("SELECT * FROM " + PharmacyDatabase.PHARMACY_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<Pharmacy> getRecordsByUserId(int loggedInUserId);

    @Query("SELECT * FROM " + PharmacyDatabase.PHARMACY_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    LiveData<List<Pharmacy>> getRecordsByUserIdLiveData(int loggedInUserId);
}
