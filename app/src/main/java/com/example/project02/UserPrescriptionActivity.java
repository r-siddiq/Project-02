package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.User;
import com.example.project02.databinding.ActivityMainBinding;
import com.example.project02.databinding.ActivityUserPrescriptionBinding;

public class UserPrescriptionActivity extends AppCompatActivity {

    private static final String PRESCRIPTION_ACTIVITY_USER_ID = "com.example.project02.PRESCRIPTION_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.project02.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityUserPrescriptionBinding binding;

    private PharmacyRepository repository;


    int loggedInUserID = LOGGED_OUT;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = PharmacyRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        invalidateOptionsMenu();
    }

    private void loginUser(Bundle savedInstanceState) {
        //Check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserID == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedInUserID == LOGGED_OUT){
            loggedInUserID = getIntent().getIntExtra(PRESCRIPTION_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInUserID == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUsersByUserId(loggedInUserID);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null){
                invalidateOptionsMenu();
            }
        });
    }

    static Intent prescriptionActivityIntentFactory(Context context, int PatientID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(PRESCRIPTION_ACTIVITY_USER_ID, PatientID);
        return intent;
    }
}