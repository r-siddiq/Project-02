package com.example.project02.Database;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.project02.Database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * from " + PharmacyDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + PharmacyDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * from " + PharmacyDatabase.USER_TABLE + " WHERE username == :username" )
    LiveData<User> getPatientByUsername(String username);

    @Query("SELECT * from " + PharmacyDatabase.USER_TABLE + " WHERE id == :userId" )
    LiveData<User> getPatientByUserId(int userId);

    @Query("SELECT COUNT(*) FROM " + PharmacyDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<Integer> countUsersByUsername(String username);
}