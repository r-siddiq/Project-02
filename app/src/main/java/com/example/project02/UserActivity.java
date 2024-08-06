package com.example.project02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcomeUser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button placeOrderButton = findViewById(R.id.placeOrder);
        placeOrderButton.setOnClickListener(v -> {
//            Intent intent = new Intent(UserActivity.this, PlaceOrderActivity.class);
//startActivity(intent);
        });

        Button orderStatusButton = findViewById(R.id.orderStatus);
        orderStatusButton.setOnClickListener(v -> {
//            Intent intent = new Intent(UserActivity.this, OrderStatusActivity.class);
//            startActivity(intent);
        });

        Button previousOrdersButton = findViewById(R.id.previousOrders);
        previousOrdersButton.setOnClickListener(v -> {
//            Intent intent = new Intent(UserActivity.this, PreviousOrdersActivity.class);
//            startActivity(intent);
        });
    }
}
