package com.example.project02;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;


import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.User;
import com.example.project02.Database.entities.Prescription;
import com.example.project02.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project02.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.project02.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;

    private PharmacyRepository repository;

    public static final String TAG = "GRP7_Pill_Hub";

    int loggedInPatientID = LOGGED_OUT;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = PharmacyRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        invalidateOptionsMenu();

        if(loggedInPatientID == LOGGED_OUT){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }


        //updateDisplay();
    }

    static Intent mainActivityIntentFactory(Context context, int PatientID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, PatientID);
        return intent;
    }

    private void loginUser(Bundle savedInstanceState) {
        //Check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInPatientID = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInPatientID == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInPatientID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedInPatientID == LOGGED_OUT){
            loggedInPatientID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInPatientID == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUsersByUserId(loggedInPatientID);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null){
                invalidateOptionsMenu();
                updateAdminViewsVisibility();
            }
        });
    }

    private void logout() {
        loggedInPatientID = LOGGED_OUT;
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void updateAdminViewsVisibility() {
        boolean isAdmin = user != null && user.isAdmin();
        binding.enterUserDelete.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
        binding.deleteUsers.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

    }

    private void showLogoutDialog(){
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alerBuilder.create();
        alerBuilder.setMessage("Logout?");
        alerBuilder.setPositiveButton("Logout?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        });

        alerBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alerBuilder.create().show();
    }

    private void deleteUser() {
        targetUsername = binding.enterUserDelete.getText().toString();
        if (targetUsername.isEmpty()) {
            toastMaker("Target username is empty.");
            return;
        }

        repository.getUsersByUserId(loggedInPatientID).observe(this, loggedInPatient -> {
            if (loggedInPatient == null) {
                toastMaker("Error getting logged-in user details.");
                return;
            }

            repository.getUserByUsername(targetUsername).observe(this, targetPatient -> {
                if (targetPatient == null) {
                    toastMaker("No such user.");
                    return;
                }

                if (loggedInPatient.getUsername().equals(targetPatient.getUsername())) {
                    toastMaker("Cannot delete your own account.");
                    return;
                }

                repository.deleteUser(targetPatient); // Use repository to delete
                toastMaker("User deleted.");
            });
        });
    }

/*    private void updateDisplay(){
        ArrayList<Prescription> allLogs = repository.getAllLogs();
        if(allLogs.isEmpty()){
        }
        StringBuilder sb = new StringBuilder();
        for(Prescription log : allLogs){
            sb.append(log);
        }
    }*/
}