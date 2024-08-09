package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project02.Database.PharmacyRepository;
import com.example.project02.databinding.ActivityInventoryBinding;
/**
 * InventoryActivity manages the inventory-related actions within the application.
 * It allows users to check inventory, add new inventory items, and navigate back to the previous screen.
 *
 * The activity uses {@link ActivityInventoryBinding} for view binding and {@link PharmacyRepository}
 * for data operations. It sets up click listeners for buttons to navigate to different activities.
 *
 * @author Saria Kabbour
 * @author Jess Hammond
 * @author Rahim Siddiq
 *
 * @since 2024-08-08
 */
public class InventoryActivity extends AppCompatActivity {

    /**
     * Key for passing user ID in Intents.
     */
    public static final String INVENTORY_ACTIVITY_USER_ID = "com.example.project02.INVENTORY_ACTIVITY_USER_ID";

    private ActivityInventoryBinding binding;
    private PharmacyRepository repository;

    /**
     * Called when the activity is first created.
     * Initializes the layout, sets up the repository, and configures button click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}. Otherwise it is null.
     */
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
                startActivity(intent);
            }
        });

        // Add Inventory button click listener
        binding.addInventory.setOnClickListener(v -> {
            Intent intent = new Intent(InventoryActivity.this, AddInventoryActivity.class);
            startActivity(intent);
        });

        // Back button click listener
        binding.backButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    /**
     * Creates an Intent for starting the {@link InventoryActivity}.
     *
     * @param context The context to use for starting the activity.
     * @param userId The user ID to pass to the activity.
     * @return The Intent to start {@link InventoryActivity}.
     */
    public static Intent inventoryIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, InventoryActivity.class);
        intent.putExtra(INVENTORY_ACTIVITY_USER_ID, userId);
        return intent;
    }
}
