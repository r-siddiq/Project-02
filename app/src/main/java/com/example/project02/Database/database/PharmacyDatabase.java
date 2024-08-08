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
import com.example.project02.Database.database.entities.Drug;
import com.example.project02.Database.database.DrugDAO;
import com.example.project02.Database.database.PatientDAO;
import com.example.project02.Database.database.PharmacyDAO;
import com.example.project02.Database.database.PrescriptionDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Patient.class, Pharmacy.class, Prescription.class, Drug.class}, version = 1, exportSchema = false)
public abstract class PharmacyDatabase extends RoomDatabase {

    public static final String DRUG_TABLE = "drug_table";
    public static final String PHARMACY_TABLE = "pharmacy_table";
    public static final String PATIENT_TABLE = "patient_table";
    public static final String PRESCRIPTION_TABLE = "prescription_table";
    private static final String DATABASE_NAME = "pharmacy_database";

    // Singleton instance
    private static volatile PharmacyDatabase instance;
    private static final int NUMBER_OF_THREADS = 4; // Maximum number of threads in database
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Returns the singleton instance of the PharmacyDatabase.
     * If the instance is not yet created, it initializes the database.
     * @param context the application context
     * @return the singleton instance of the PharmacyDatabase
     */
    public static PharmacyDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (PharmacyDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    PharmacyDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return instance;
    }

    // Abstract methods to provide DAO instances
    public abstract DrugDAO drugDAO();
    public abstract PatientDAO patientDAO();
    public abstract PharmacyDAO pharmacyDAO();
    public abstract PrescriptionDAO prescriptionDAO();
}
