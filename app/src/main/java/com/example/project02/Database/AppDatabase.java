package com.example.project02.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project02.Database.entities.Patient;
import com.example.project02.Database.entities.Prescription;
import com.example.project02.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Patient.class, Prescription.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String PATIENT_TABLE = "patientTable";
    public static final String PRESCRIPTION_TABLE = "prescriptionTable";
    private static final String DATABASE_NAME = "pillHub_database";

    // Singleton method
    private static volatile AppDatabase instance;
    private static final int NUMBER_OF_THREADS = 4; //Maximum number of threads in database
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return instance;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                PatientDAO dao = instance.patientDAO();
                dao.deleteAll();
                Patient admin = new Patient("admin1", "admin1");
                admin.setAddmin(true);
                dao.insert(admin);
                Patient testUser1 = new Patient("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }

    };

    public abstract PatientDAO patientDAO();

    public abstract PrescriptionDAO prescriptionDAO();
}