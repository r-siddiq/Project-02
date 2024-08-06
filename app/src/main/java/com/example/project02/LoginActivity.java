package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02.Database.AppRepository;
import com.example.project02.Database.entities.Patient;
import com.example.project02.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        //Login button clicked
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    verifyUser();
            }
        });

        //SignUp button clicked
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.signUpIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }

    private void verifyUser(){
        String username = binding.enterUsername.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username should not be blank.");
            return;
        }
        LiveData<Patient> userObserver = repository.getPatientByUsername(username);
        userObserver.observe(this, user -> {
            if(user != null){
                String password = binding.enterPassword.getText().toString();
                if(password.equals(user.getPassword())){
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                }else{
                    toastMaker("Invalid username or password");
                    binding.enterPassword.setSelection(0);
                }
            }else{
                toastMaker(String.format("%s is not a valid username.", username));
                binding.enterUsername.setSelection(0);
            }
        });
    }
}