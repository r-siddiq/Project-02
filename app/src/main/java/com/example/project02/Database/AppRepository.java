package com.example.project02.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project02.Database.entities.Patient;
import com.example.project02.Database.entities.Prescription;
import com.example.project02.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {

    private final PatientDAO patientDAO;
    private final PrescriptionDAO prescriptionDAO;

    private static AppRepository repository;


    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.patientDAO = db.patientDAO();
        this.prescriptionDAO = db.prescriptionDAO();
    }

    public static AppRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<AppRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting AppRepository, thread error.");
        }
        return null;
    }

    public ArrayList<Prescription> getAllLogs() {
        Future<ArrayList<Prescription>> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Prescription>>() {
                    @Override
                    public ArrayList<Prescription> call() throws Exception {
                        return (ArrayList<Prescription>) prescriptionDAO.getAllRecords();
                    }
                });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository.");
        }
        return null;
    }

    public void insertUser(Patient patient){
        AppDatabase.databaseWriteExecutor.execute(()-> {
            patientDAO.insert(patient);
        });
    }

    public LiveData<Patient> getPatientByUsername(String username) {
        return patientDAO.getPatientByUsername(username);
    }

    public LiveData<Patient> getPatientByUserId(int userId) {
        return patientDAO.getPatientByUserId(userId);
    }

    public LiveData<Integer> getUserCountByUsername(String username){
        return patientDAO.countUsersByUsername(username);
    }

    public LiveData<List<Patient>> getAllPatientsByUserId(int patientId) {
        return patientDAO.getAllPatientsByPatientIdLiveData(patientId);
    }

    public void deletePatientByUsername(String username){
        AppDatabase.databaseWriteExecutor.execute(() -> patientDAO.deleteByUsername(username));
    }

    public void deletePatient(Patient patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            patientDAO.delete(patient);
        });
    }
}
