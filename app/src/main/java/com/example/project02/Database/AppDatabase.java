package com.example.project02.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.project02.Database.entities.Patient;
import com.example.project02.Database.entities.Prescription;
import com.example.project02.Database.entities.Pharmacy;

@Database(entities = {Patient.class, Pharmacy.class, Prescription.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // Singleton Instance
    private static volatile AppDatabase instance;

}
