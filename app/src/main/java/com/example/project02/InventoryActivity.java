package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.Drug;
import com.example.project02.R;
import com.example.project02.databinding.ActivityInventoryBinding;

public class InventoryActivity extends AppCompatActivity {
    public static final String INVENTORY_ACTIVITY_USER_ID = "com.example.project02.INVENTORY_ACTIVITY_USER_ID";


    private ActivityInventoryBinding binding;
    private PharmacyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        repository = PharmacyRepository.getRepository(getApplication());

        EditText drugNameEditText = findViewById(R.id.drugNameEditText);
        Button addDrugButton = findViewById(R.id.addDrugButton);

        addDrugButton.setOnClickListener(v -> {
            String drugName = drugNameEditText.getText().toString().trim();

            if (drugName.isEmpty()) {
                Toast.makeText(this, "Please enter a drug name.", Toast.LENGTH_SHORT).show();
                return;
            }

            Drug newDrug = new Drug(drugName);
            repository.insertDrug(newDrug);

            Toast.makeText(this, "Drug added to inventory!", Toast.LENGTH_SHORT).show();

            drugNameEditText.setText("");
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
    public static Intent inventoryIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, InventoryActivity.class);
        intent.putExtra(INVENTORY_ACTIVITY_USER_ID, userId);
        return intent;
    }



}





//        Button checkInventoryButton = findViewById(R.id.checkInventory);
//        checkInventoryButton.setOnClickListener(v -> {
////            Intent intent = new Intent(InventoryActivity.this, CheckInventoryActivity.class);
////            startActivity(intent);
//        });
//
//        Button addInventoryButton = findViewById(R.id.addInventory);
//        addInventoryButton.setOnClickListener(v -> {
////            Intent intent = new Intent(InventoryActivity.this, AddInventoryActivity.class);
////            startActivity(intent);
//        });
//
//        Button removeInventoryButton = findViewById(R.id.removeInventory);
//        removeInventoryButton.setOnClickListener(v -> {
////            Intent intent = new Intent(InventoryActivity.this, RemoveInventoryActivity.class);
////            startActivity(intent);
//        });
//    }
//}