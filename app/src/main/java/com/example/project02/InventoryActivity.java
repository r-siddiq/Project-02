package com.example.project02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InventoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inventory_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inventoryLabel), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button checkInventoryButton = findViewById(R.id.checkInventory);
        checkInventoryButton.setOnClickListener(v -> {
//            Intent intent = new Intent(InventoryActivity.this, CheckInventoryActivity.class);
//            startActivity(intent);
        });

        Button addInventoryButton = findViewById(R.id.addInventory);
        addInventoryButton.setOnClickListener(v -> {
//            Intent intent = new Intent(InventoryActivity.this, AddInventoryActivity.class);
//            startActivity(intent);
        });

        Button removeInventoryButton = findViewById(R.id.removeInventory);
        removeInventoryButton.setOnClickListener(v -> {
//            Intent intent = new Intent(InventoryActivity.this, RemoveInventoryActivity.class);
//            startActivity(intent);
        });
    }
}