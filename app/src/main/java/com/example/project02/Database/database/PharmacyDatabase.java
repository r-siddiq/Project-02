package com.example.project02.Database.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.project02.Database.database.entities.Patient;
import com.example.project02.Database.database.entities.Prescription;
import com.example.project02.Database.database.entities.Pharmacy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Patient.class, Pharmacy.class, Prescription.class}, version = 1, exportSchema = false)
public abstract class PharmacyDatabase extends RoomDatabase {


    // Singleton method
    private static volatile PharmacyDatabase instance;
    private static final int NUMBER_OF_THREADS = 4; //Maximum number of threads in database
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PharmacyDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (PharmacyDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    PharmacyDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDeafultValues)
                            .build();
                }
            }
        }
        return instance;
    }
    private static final RoomDatabase.Callback addDeafultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            //Log.i(MainActivity.TAG, "DATABASE CREATED!")
            //TODO:Add TAG to MainActivity
            //TODO: add databaseWriteExecutor.execute(() ->{...}
        }

    };
}