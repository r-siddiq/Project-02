package com.example.project02.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.Prescription;

import java.util.List;

public class PrescriptionViewModel extends AndroidViewModel {

    private final PharmacyRepository repository;

    public PrescriptionViewModel(Application application){
        super(application);
        repository = PharmacyRepository.getRepository(application);
    }

    public LiveData<List<Prescription>> getAllLogsById(int userId) {
        return repository.getAllLogsByUserIdLiveData(userId);
    }

    public void insert(Prescription log){
        repository.insertPrescription(log);
    }
}
