package com.example.project02.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project02.Database.entities.User;
import com.example.project02.Database.entities.Prescription;
import com.example.project02.Database.entities.Pharmacy;
import com.example.project02.Database.entities.Drug;
import com.example.project02.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Pharmacy.class, Prescription.class, Drug.class}, version = 1, exportSchema = false)
public abstract class PharmacyDatabase extends RoomDatabase {

    public static final String DRUG_TABLE = "drug_table";
    public static final String PHARMACY_TABLE = "pharmacy_table";
    public static final String USER_TABLE = "user_table";
    public static final String PRESCRIPTION_TABLE = "prescription_table";
    private static final String DATABASE_NAME = "pharmacy_database";

    // Singleton instance
    private static volatile PharmacyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4; // Maximum number of threads in database
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Returns the singleton instance of the PharmacyDatabase.
     * If the instance is not yet created, it initializes the database.
     * @param context the application context
     * @return the singleton instance of the PharmacyDatabase
     */
    static PharmacyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PharmacyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PharmacyDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database Created");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testUser1", "testUser1");
                dao.insert(testUser1);
            });
        }
    };

    // Abstract methods to provide DAO instances
    public abstract DrugDAO drugDAO();
    public abstract PharmacyDAO pharmacyDAO();
    public abstract PrescriptionDAO prescriptionDAO();
    public abstract UserDAO userDAO();
}
