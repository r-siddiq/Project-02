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
    List<GymLog> getAllRecords();

    /**
     * Retrieves all GymLog records for a specific user, ordered by date in descending order.
     * @param loggedInUserId the ID of the user whose records are to be retrieved
     * @return a list of GymLog records for the specified user
     */
    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<GymLog> getRecordsByUserId(int loggedInUserId);

    /**
     * Retrieves all GymLog records for a specific user as LiveData, ordered by date in descending order.
     * This allows for observing changes to the data in real-time.
     * @param loggedInUserId the ID of the user whose records are to be retrieved
     * @return a LiveData list of GymLog records for the specified user
     */
    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    LiveData<List<GymLog>> getRecordsByUserIdLiveData(int loggedInUserId);
}
