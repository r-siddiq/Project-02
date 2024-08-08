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
import com.example.project02.Database.entities.User;
import com.example.project02.databinding.ActivityAdminActionBinding;

public class AdminActionActivity extends AppCompatActivity {

    private PharmacyRepository repository;
    private ActivityAdminActionBinding binding;

    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.project02.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final String ADMIN_ACTIVITY_USER_ID = "com.example.project02.ADMIN_ACTIVITY_USER_ID";

    private static final int LOGGED_OUT = -1;
    int loggedInUserID = LOGGED_OUT;

    String targetUsername;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminActionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = PharmacyRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        invalidateOptionsMenu();


        binding.deleteUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        binding.makeAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAdmin();
            }
        });

        binding.demoteAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demoteAdmin();
            }
        });

    }
    static Intent adminActionIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, AdminActionActivity.class);
        intent.putExtra(ADMIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void deleteUser() {
        targetUsername = binding.enterUsernameAdmin.getText().toString();
        if (targetUsername.isEmpty()) {
            toastMaker("Target username is empty.");
            return;
        }

        // Observe the logged-in user details
        LiveData<User> loggedInUserLiveData = repository.getUsersByUserId(loggedInUserID);
        loggedInUserLiveData.observe(this, loggedInUser -> {
            if (loggedInUser == null) {
                toastMaker("Error getting logged-in user details.");
                loggedInUserLiveData.removeObservers(this); // Clean up observer
                return;
            }

            // Observe the target user details
            LiveData<User> targetUserLiveData = repository.getUserByUsername(targetUsername);
            targetUserLiveData.observe(this, targetUser -> {
                if (targetUser == null) {
                    toastMaker("No such user.");
                    loggedInUserLiveData.removeObservers(this); // Clean up observer
                    targetUserLiveData.removeObservers(this); // Clean up observer
                    return;
                } else if (loggedInUser.getUsername().equals(targetUser.getUsername())) {
                    toastMaker("Cannot delete your own account.");
                    loggedInUserLiveData.removeObservers(this); // Clean up observer
                    targetUserLiveData.removeObservers(this); // Clean up observer
                    return;
                }

                // Perform the user deletion
                repository.deleteUser(targetUser);
                toastMaker("User deleted.");

                // Clean up observers after operation
                loggedInUserLiveData.removeObservers(this);
                targetUserLiveData.removeObservers(this);
            });
        });
    }

    private void makeAdmin() {
        targetUsername = binding.enterUsernameAdmin.getText().toString();
        if (targetUsername.isEmpty()) {
            toastMaker("Target username is empty.");
            return;
        }

        // Observe the logged-in user details
        LiveData<User> loggedInUserLiveData = repository.getUsersByUserId(loggedInUserID);
        loggedInUserLiveData.observe(this, loggedInUser -> {
            if (loggedInUser == null) {
                toastMaker("Error getting logged-in user details.");
                loggedInUserLiveData.removeObservers(this); // Clean up observer
                return;
            }

            // Observe the target user details
            LiveData<User> targetUserLiveData = repository.getUserByUsername(targetUsername);
            targetUserLiveData.observe(this, targetUser -> {
                if (targetUser == null) {
                    toastMaker("No such user.");
                    loggedInUserLiveData.removeObservers(this); // Clean up observer
                    targetUserLiveData.removeObservers(this); // Clean up observer
                    return;
                } else if (targetUser.isAdmin()) {
                    toastMaker("User is already an admin.");
                    loggedInUserLiveData.removeObservers(this); // Clean up observer
                    targetUserLiveData.removeObservers(this); // Clean up observer
                    return;
                }

                // Perform the admin promotion
                repository.makeAdmin(targetUser);
                toastMaker(String.format("%s is now an admin.", targetUsername));

                // Clean up observers after operation
                loggedInUserLiveData.removeObservers(this);
                targetUserLiveData.removeObservers(this);
            });
        });
    }

    private void demoteAdmin() {
        targetUsername = binding.enterUsernameAdmin.getText().toString();
        if (targetUsername.isEmpty()) {
            toastMaker("Target username is empty.");
            return;
        }

        // Observe the logged-in user details
        LiveData<User> loggedInUserLiveData = repository.getUsersByUserId(loggedInUserID);
        loggedInUserLiveData.observe(this, loggedInUser -> {
            if (loggedInUser == null) {
                toastMaker("Error getting logged-in user details.");
                loggedInUserLiveData.removeObservers(this); // Clean up observer
                return;
            }

            // Observe the target user details
            LiveData<User> targetUserLiveData = repository.getUserByUsername(targetUsername);
            targetUserLiveData.observe(this, targetUser -> {
                if (targetUser == null) {
                    toastMaker("No such user.");
                    loggedInUserLiveData.removeObservers(this); // Clean up observer
                    targetUserLiveData.removeObservers(this); // Clean up observer
                    return;
                }

                // Check if the target user is an admin
                if (!targetUser.isAdmin()) {
                    toastMaker("User is not an admin.");
                    loggedInUserLiveData.removeObservers(this); // Clean up observer
                    targetUserLiveData.removeObservers(this); // Clean up observer
                    return;
                }

                // Check if the logged-in user is trying to demote themselves
                if (loggedInUser.getUsername().equals(targetUser.getUsername())) {
                    toastMaker("Cannot demote your own account.");
                    loggedInUserLiveData.removeObservers(this); // Clean up observer
                    targetUserLiveData.removeObservers(this); // Clean up observer
                    return;
                }

                // Perform the demotion
                repository.removeAdmin(targetUser);
                toastMaker(String.format("%s is no longer an admin.", targetUsername));

                // Clean up observers after operation
                loggedInUserLiveData.removeObservers(this);
                targetUserLiveData.removeObservers(this);
            });
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loginUser(Bundle savedInstanceState) {
        //Check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserID == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedInUserID == LOGGED_OUT){
            loggedInUserID = getIntent().getIntExtra(ADMIN_ACTIVITY_USER_ID, LOGGED_OUT);
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
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(AdminActionActivity.this);
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
        getIntent().putExtra(ADMIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }
}