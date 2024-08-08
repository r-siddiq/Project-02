package com.example.project02;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.User;
import com.example.project02.databinding.ActivitySignupBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private PharmacyRepository repository;

    String newUsername;
    String newPassword;
    String reenterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = PharmacyRepository.getRepository(getApplication());

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInformationFromDisplay();
            }
        });
    }

    public static Intent signUpIntentFactory(Context context){
            return new Intent(context, SignUpActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signup_cancel, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.signUpCancel);
        item.setVisible(true);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                showReturnToLogin();
                return false;
            }
        });
        return true;
    }

    private void showReturnToLogin(){
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(SignUpActivity.this);
        final AlertDialog alertDialog = alerBuilder.create();
        alerBuilder.setMessage("Return to Login?");
        alerBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
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

    private void getInformationFromDisplay(){
        newUsername = binding.enterSignUpUsername.getText().toString();
        newPassword = binding.enterSignUpPassword.getText().toString();
        reenterPassword = binding.reEnterSignUpPassword.getText().toString();
        if(!newPassword.equals(reenterPassword)){
            toastMaker("Passwords do not match.");
            return;
        }
        insertNewUser();
    }

    private void insertNewUser() {
        if (newUsername.isEmpty()) {
            toastMaker("Username cannot be empty.");
            return;
        }

        // Create a LiveData object to observe user count
        LiveData<Integer> userCountLiveData = repository.getUserCountByUsername(newUsername);

        // Create a one-time observer
        Observer<Integer> userCountObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                if (count != null) {
                    if (count == 0) {
                        // No existing user with the same username
                        User user = new User(newUsername, newPassword);
                        repository.insertUser(user);
                        toastMaker("User added.");

                        // Redirect to login screen after user is added
                        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                        startActivity(intent);
                    } else {
                        // User already exists
                        toastMaker("User already exists.");
                    }

                    // Clean up observer after the operation is complete
                    userCountLiveData.removeObserver(this);
                }
            }
        };

        // Observe the user count LiveData
        userCountLiveData.observe(this, userCountObserver);
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}