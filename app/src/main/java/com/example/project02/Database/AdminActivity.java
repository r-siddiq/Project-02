package com.example.project02.Database;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project02.R;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcomeAdmin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button orderRequests = findViewById(R.id.orderRequests);
        orderRequests.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, OrderRequestsActivity.class);
            startActivity(intent);
        });

        Button inventory = findViewById(R.id.inventory);
        inventory.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, InventoryActivity.class);
            startActivity(intent);
        });

        Button previousOrdersAdmin = findViewById(R.id.previousOrdersAdmin);
        previousOrdersAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, PreviousOrdersActivity.class);
            startActivity(intent);
        });

        Button customerInfo = findViewById(R.id.customerInfo);
        customerInfo.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, CustomerInfoActivity.class);
            startActivity(intent);
        });
    }
}
