package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.User;
import com.example.project02.databinding.ActivityMainBinding;
import com.example.project02.databinding.ActivityPrescriptionEntryBinding;

public class PrescriptionEntryActivity extends AppCompatActivity {
    private static final String PENTRY_ACTIVITY_USER_ID = "com.example.project02.PENTRY_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.project02.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityPrescriptionEntryBinding binding;

    private PharmacyRepository repository;


    int loggedInUserID = LOGGED_OUT;
    private User user;

    public static Intent prescriptionEntryIntentFactory(Context applicationContext, int userID) {
        Intent intent = new Intent(applicationContext, PrescriptionEntryActivity.class);
        intent.putExtra(PENTRY_ACTIVITY_USER_ID, userID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescriptionEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = PharmacyRepository.getRepository(getApplication());
        //loginUser(savedInstanceState);
        invalidateOptionsMenu();
    }
}
