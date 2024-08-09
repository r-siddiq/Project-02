package com.example.project02.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02.Database.entities.Drug;

import java.util.List;

@Dao
public interface DrugDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Drug... drug);

    @Delete
    void delete(Drug drug);

    @Query("SELECT * FROM " + PharmacyDatabase.DRUG_TABLE + " ORDER BY name")
    LiveData<Drug> getAllDrugs();

    @Query("DELETE FROM " + PharmacyDatabase.DRUG_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + PharmacyDatabase.DRUG_TABLE + " WHERE name == :drugName")
    LiveData<Drug> getDrugByName(String drugName);

    @Query("SELECT * FROM " + PharmacyDatabase.DRUG_TABLE + " WHERE id == :id")
    LiveData<Drug> getDrugById(int id);
    //LiveData for CheckInventoryActivity
    @Query("SELECT * FROM " + PharmacyDatabase.DRUG_TABLE + " ORDER BY name")
    LiveData<List<Drug>> getAllDrugsList();

    @Update
    void update(Drug drug);
}