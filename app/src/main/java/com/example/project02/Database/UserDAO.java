package com.example.project02.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02.Database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + PharmacyDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM " + PharmacyDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + PharmacyDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM " + PharmacyDatabase.USER_TABLE + " WHERE id = :userId")
    LiveData<User> getUsersByUserId(int userId);

    @Query("SELECT COUNT(*) FROM " + PharmacyDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<Integer> countUsersByUsername(String username);

    @Query("DELETE FROM " + PharmacyDatabase.USER_TABLE + " WHERE username = :username")
    void deleteByUsername(String username);

    @Query("SELECT * FROM " + PharmacyDatabase.USER_TABLE + " WHERE id = :userId")
    LiveData<List<User>> getAllUsersByUserIdLiveData(int userId);

    @Update
    void update(User user);
}