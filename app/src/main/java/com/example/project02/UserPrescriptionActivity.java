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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.User;
import com.example.project02.databinding.ActivityUserPrescriptionBinding;
import com.example.project02.viewHolders.PrescriptionAdapter;
import com.example.project02.viewHolders.PrescriptionViewModel;

public class UserPrescriptionActivity extends AppCompatActivity {

    private static final String PRESCRIPTION_ACTIVITY_USER_ID = "com.example.project02.PRESCRIPTION_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.project02.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityUserPrescriptionBinding binding;
    private PharmacyRepository pharmacyRepository;
    private RecyclerView recyclerView;
    private PrescriptionAdapter adapter;

    private PharmacyRepository repository;
    private PrescriptionViewModel prescriptionViewModel;


    int loggedInUserID = LOGGED_OUT;
    private User user;

    public static Intent prescriptionActivityIntentFactory(Context applicationContext, int userID) {
        Intent intent = new Intent(applicationContext, UserPrescriptionActivity.class);
        intent.putExtra(PRESCRIPTION_ACTIVITY_USER_ID, userID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prescriptionViewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);

        RecyclerView recyclerView = binding.recyclerViewPrescriptions;
        final PrescriptionAdapter adapter = new PrescriptionAdapter(new PrescriptionAdapter.PrescriptionDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = PharmacyRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        repository.getUsersByUserId(loggedInUserID).observe(this, user -> {
            if (user != null) {
                this.user = user;
                String username = user.getUsername();
                prescriptionViewModel.getAllLogsByUsername(username).observe(this, prescriptions -> {
                    adapter.submitList(prescriptions);
                });
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserID));
            }
        });
    }

    private void loginUser(Bundle savedInstanceState) {
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
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(UserPrescriptionActivity.this);
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
        getIntent().putExtra(PRESCRIPTION_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }
}