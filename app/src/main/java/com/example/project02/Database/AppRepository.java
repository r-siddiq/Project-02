package com.example.project02.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project02.Database.entities.Patient;
import com.example.project02.MainActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {

    private final PatientDAO patientDAO;

    private static AppRepository repository;


    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.patientDAO = db.patientDAO();
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

    public void insertuser(Patient...patient){
        AppDatabase.databaseWriteExecutor.execute(()-> {
            patientDAO.insert(patient);
        });
    }

    public LiveData<Patient> getPatientByUsername(String username) {
        return patientDAO.getUserByUsername(username);
    }

    public LiveData<Patient> getPatientByUserId(int userId) {
        return patientDAO.getUserByUserId(userId);
    }
}
