package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project02.Database.PharmacyRepository;
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
        //loginUser(savedInstanceState);
        invalidateOptionsMenu();

        // Check Inventory button click listener
        binding.checkInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this, CheckInventoryActivity.class);
                startActivity(intent);            }
        });

        // Add Inventory button click listener
        binding.addInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this, AddInventoryActivity.class); startActivity(intent);            }
        });

        // Remove Inventory button click listener
        binding.removeInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement logic to remove inventory items or remove option
            }
        });
    }

    public static Intent inventoryIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, InventoryActivity.class);
        intent.putExtra(INVENTORY_ACTIVITY_USER_ID, userId);
        return intent;
    }
}

