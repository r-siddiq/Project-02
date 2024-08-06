package com.example.project02;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project02.Database.AppRepository;

public class SignUpActivity extends AppCompatActivity {

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = AppRepository.getRepository(getApplication());
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
                showReturntoLogin();
                return false;
            }
        });
        return true;
    }

    private void showReturntoLogin(){
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
}