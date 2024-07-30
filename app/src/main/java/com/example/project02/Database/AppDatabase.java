package com.example.project02.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project02.Database.entities.Patient;

@Database(entities = {Patient.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String patientTable = "patientTable";

    // Define DAOs
    public abstract PatientDAO patientDao();


    // Singleton instance
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
