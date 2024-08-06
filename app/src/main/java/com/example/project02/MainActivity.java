package com.example.project02;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project02.Database.AppRepository;
import com.example.project02.Database.entities.Patient;
import com.example.project02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project02.MAIN_ACTIVITY_USER_ID";
    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;

    private AppRepository repository;

    public static final String TAG = "GRP7_Pill_Hub";

    int loggedInPatientID = LOGGED_OUT;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();
        invalidateOptionsMenu();

        if(loggedInPatientID == LOGGED_OUT){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        repository = AppRepository.getRepository(getApplication());



/*        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button signUpButton = findViewById(R.id.signUp);
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });*/
    }

    static Intent mainActivityIntentFactory(Context context, int PatientID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, PatientID);
        return intent;
    }

    private void loginUser() {
        //TODO: make login method functional
        patient = new Patient("Jess", "password");
        loggedInPatientID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    private void logout() {
        //TODO: finish logout method
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
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
}