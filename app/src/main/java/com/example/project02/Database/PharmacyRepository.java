package com.example.project02.Database;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.project02.Database.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * PharmacyRepository provides a layer of abstraction over the data sources.
 * It manages queries and allows access to the database using the DAO interfaces.
 */
public class PharmacyRepository {

    private final DrugDAO drugDAO;
    private final UserDAO userDAO;
    private final PharmacyDAO pharmacyDAO;
    private final PrescriptionDAO prescriptionDAO;
    private ArrayList<Pharmacy> pLogs;
    private static PharmacyRepository repository;


    public LiveData<List<Drug>> getAllDrugsList() {
        return drugDAO.getAllDrugsList();
    }

    /**
     * Private constructor for PharmacyRepository.
     * Initializes DAO interfaces and retrieves all records from the database.
     * @param application the application context
     */
    private PharmacyRepository(Application application) {
        PharmacyDatabase db = PharmacyDatabase.getDatabase(application);
        this.drugDAO = db.drugDAO();
        this.userDAO = db.userDAO();
        this.pharmacyDAO = db.pharmacyDAO();
        this.prescriptionDAO = db.prescriptionDAO();
        this.pLogs = (ArrayList<Pharmacy>) this.pharmacyDAO.getAllRecords();
    }

    /**
     * Returns the singleton instance of PharmacyRepository.
     * If the instance is not yet created, it initializes it using the application context.
     * @param application the application context
     * @return the singleton instance of PharmacyRepository
     */
    public static PharmacyRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<PharmacyRepository> future = PharmacyDatabase.databaseWriteExecutor.submit(new Callable<PharmacyRepository>() {
            @Override
            public PharmacyRepository call() throws Exception {
                return new PharmacyRepository(application);
            }
        });
        try {
            repository = future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("PharmacyRepository", "Problem when creating repository");
        }
        return repository;
    }

    /**
     * Inserts a new Drug record into the database.
     * @param drug the Drug to insert
     */
    public void insertDrug(Drug drug) {
        PharmacyDatabase.databaseWriteExecutor.execute(() -> drugDAO.insert(drug));
    }

    /**
     * Updates an existing Drug record in the database.
     * @param drug the Drug to update
     */
    public void updateDrug(Drug drug) {
        PharmacyDatabase.databaseWriteExecutor.execute(() -> drugDAO.update(drug));
    }

    /**
     * Deletes a Drug record from the database.
     * @param drug the Drug to delete
     */
    public void deleteDrug(Drug drug) {
        PharmacyDatabase.databaseWriteExecutor.execute(() -> drugDAO.delete(drug));
    }

    /**
     * Retrieves a Drug record by ID.
     * @param drugName the name of the Drug
     * @return the Drug record with the specified name
     */
    public LiveData<Drug> getDrugByName(String drugName) {
        return drugDAO.getDrugByName(drugName);
    }

    public LiveData<Drug> getDrugById(int id) {
        return drugDAO.getDrugByName(String.valueOf(id));
    }


    /**
     * Retrieves all Drug records from the database.
     * @return a LiveData list of all Drug records
     */
    public LiveData<Drug> getAllDrugs() {
        return drugDAO.getAllDrugs();
    }

    public void insertUser(User user){
        PharmacyDatabase.databaseWriteExecutor.execute(()-> {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUsersByUserId(int userId) {
        return userDAO.getUsersByUserId(userId);
    }

    public LiveData<Integer> getUserCountByUsername(String username){
        return userDAO.countUsersByUsername(username);
    }

    public LiveData<List<User>> getAllUsersByUserId(int patientId) {
        return userDAO.getAllUsersByUserIdLiveData(patientId);
    }

    public void deleteUserByUsername(String username){
        PharmacyDatabase.databaseWriteExecutor.execute(() -> userDAO.deleteByUsername(username));
    }

    public void deleteUser(User user) {
        PharmacyDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(user);
        });
    }

    /**
     * Promotes a user to admin.
     * @param user the User object with updated admin status
     */
    public void makeAdmin(User user) {
        user.setAdmin(true);
        PharmacyDatabase.databaseWriteExecutor.execute(() -> userDAO.update(user));
    }

    /**
     * Demotes a user from admin.
     * @param user the User object with updated admin status
     */
    public void removeAdmin(User user) {
        user.setAdmin(false);
        PharmacyDatabase.databaseWriteExecutor.execute(() -> userDAO.update(user));
    }

}