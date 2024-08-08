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

import com.example.project02.Database.AppRepository;
import com.example.project02.Database.entities.User;
import com.example.project02.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AppRepository repository;

    String newUsername;
    String newPassword;
    String reenterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

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

    private void insertNewUser(){
        if(newUsername.isEmpty()){
            return;
        }

        User user = new User(newUsername, newPassword);
        repository.getUserCountByUsername(newUsername).observe(this, count -> {
            if(count != null){
                if(count == 0){
                    repository.insertUser(user);
                    toastMaker("User added.");
                    Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                    startActivity(intent);
                }else {
                    toastMaker("User already exists.");
                }
            }
        });

    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}