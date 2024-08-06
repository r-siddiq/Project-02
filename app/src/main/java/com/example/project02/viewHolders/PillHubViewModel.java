package com.example.project02.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project02.Database.AppRepository;
import com.example.project02.Database.entities.Patient;

import java.util.List;

public class PillHubViewModel extends AndroidViewModel {
    private final AppRepository repository;

    public PillHubViewModel(Application application){
        super(application);
        repository = AppRepository.getRepository(application);
    }

    public LiveData<List<Patient>> getAllPatientsById(int patientId) {
        return repository.getAllPatientsByUserId(patientId);
    }
}
