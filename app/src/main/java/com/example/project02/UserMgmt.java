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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.Database.AppDatabase;
import com.example.project02.Database.AppRepository;
import com.example.project02.Database.PatientDAO;
import com.example.project02.Database.entities.Patient;
import com.example.project02.Database.entities.Prescription;
import com.example.project02.databinding.ActivityUserMgmtBinding;
import com.example.project02.viewHolders.PillHubAdapter;
import com.example.project02.viewHolders.PillHubViewModel;

import java.util.ArrayList;

public class UserMgmt extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    int loggedInPatientID = LOGGED_OUT;
    private Patient patient;

    private static final String ADMIN_ACTIVITY_USER_ID = "com.example.project02.ADMIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.project02.SAVED_INSTANCE_STATE_USERID_KEY";
    private AppRepository repository;
    private PillHubViewModel pillHubViewModel;
    private ActivityUserMgmtBinding binding;

    String targetUsername;

    public static Intent userMgmtIntentFactory(Context applicationContext, int patientId) {
        Intent intent = new Intent(applicationContext, UserMgmt.class);
        intent.putExtra(ADMIN_ACTIVITY_USER_ID, patientId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserMgmtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        pillHubViewModel = new ViewModelProvider(this).get(PillHubViewModel.class);

        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        final PillHubAdapter adapter = new PillHubAdapter(new PillHubAdapter.PatientDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = AppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        pillHubViewModel.getAllPatientsById(loggedInPatientID).observe(this, patients -> {
            adapter.submitList(patients);
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AdminActivity.adminActivityIntentFactory(getApplicationContext(), loggedInPatientID));
            }
        });

        binding.deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

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
        if(patient == null){
            return false;
        }
        item.setTitle(patient.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(UserMgmt.this);
        final AlertDialog alertDialog = alerBuilder.create();
        alerBuilder.setMessage("Logout?");
        alerBuilder.setPositiveButton("Logout?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {logout();}
        });

        alerBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alerBuilder.create().show();
    }

    private void updateDisplay(){
        ArrayList<Prescription> allLogs = repository.getAllLogs();
        if(allLogs.isEmpty()){
        }
        StringBuilder sb = new StringBuilder();
        for(Prescription log : allLogs){
            sb.append(log);
        }
    }

    private void logout() {
        loggedInPatientID = LOGGED_OUT;
        getIntent().putExtra(ADMIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInPatientID = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInPatientID == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInPatientID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedInPatientID == LOGGED_OUT){
            loggedInPatientID = getIntent().getIntExtra(ADMIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInPatientID == LOGGED_OUT){
            return;
        }
        LiveData<Patient> userObserver = repository.getPatientByUserId(loggedInPatientID);
        userObserver.observe(this, user -> {
            this.patient = user;
            if(this.patient != null){
                invalidateOptionsMenu();
            }
        });
    }

    private void deleteUser() {
        targetUsername = binding.enterPatient.getText().toString();
        if (targetUsername.isEmpty()) {
            toastMaker("Target username is empty.");
            return;
        }

        repository.getPatientByUserId(loggedInPatientID).observe(this, loggedInPatient -> {
            if (loggedInPatient == null) {
                toastMaker("Error getting logged-in user details.");
                return;
            }

            repository.getPatientByUsername(targetUsername).observe(this, targetPatient -> {
                if (targetPatient == null) {
                    toastMaker("No such user.");
                    return;
                }

                if (loggedInPatient.getUsername().equals(targetPatient.getUsername())) {
                    toastMaker("Cannot delete your own account.");
                    return;
                }

                repository.deletePatient(targetPatient); // Use repository to delete
                toastMaker("User deleted.");
            });
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}