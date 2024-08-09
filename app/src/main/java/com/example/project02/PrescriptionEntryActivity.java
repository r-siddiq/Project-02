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

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.Drug;
import com.example.project02.Database.entities.Prescription;
import com.example.project02.Database.entities.User;
import com.example.project02.databinding.ActivityPrescriptionEntryBinding;

public class PrescriptionEntryActivity extends AppCompatActivity {
    private static final String PENTRY_ACTIVITY_USER_ID = "com.example.project02.PENTRY_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.project02.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityPrescriptionEntryBinding binding;

    private PharmacyRepository repository;


    int loggedInUserID = LOGGED_OUT;
    private User user;

    private String username;
    private String drugName;;

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
        loginUser(savedInstanceState);
        invalidateOptionsMenu();

        binding.createPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInformationFromDisplay();
            }
        });

        binding.prescriptionBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserID));
            }
        });
    }

    private void loginUser(Bundle savedInstanceState) {
        //Check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserID == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedInUserID == LOGGED_OUT){
            loggedInUserID = getIntent().getIntExtra(PENTRY_ACTIVITY_USER_ID, LOGGED_OUT);
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

    private void showLogoutDialog(){
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(PrescriptionEntryActivity.this);
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

    private void logout() {
        loggedInUserID = LOGGED_OUT;
        getIntent().putExtra(PENTRY_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void getInformationFromDisplay() {
        username = binding.enterName.getText().toString();
        drugName = binding.drugName.getText().toString();

        String quantityString = binding.drugQuantity.getText().toString();
        String refillsString = binding.drugRefills.getText().toString();

        int drugQuantity = 0;
        int drugRefills = 0;

        try {
            drugQuantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {
            toastMaker("Invalid quantity format.");
            return;
        }

        try {
            drugRefills = Integer.parseInt(refillsString);
        } catch (NumberFormatException e) {
            toastMaker("Invalid refills format.");
            return;
        }

        // Check if the drug name exists in the database
        LiveData<Drug> drugLiveData = repository.getDrugByName(drugName);
        int finalDrugQuantity = drugQuantity;
        int finalDrugRefills = drugRefills;
        drugLiveData.observe(this, drug -> {
            if (drug == null) {
                toastMaker(String.format("%s is not in inventory.", drugName));
            } else {
                // Proceed with inserting the prescription or other actions
                insertNewPrescription(finalDrugQuantity, finalDrugRefills);
            }
        });
    }

    private void insertNewPrescription(int quantity, int refills) {
        // Assuming you have a method to insert a new prescription
        Prescription prescription = new Prescription(drugName, quantity, username, refills);
        repository.insertPrescription(prescription);
        toastMaker("Prescription added successfully.");
    }

        private void toastMaker(String message) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
}
