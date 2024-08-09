package com.example.project02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.Drug;
import com.example.project02.databinding.ActivityAddInventoryBinding;

/**
 * Activity to add a new drug to the inventory.
 * Provides an interface for the user to input the drug's name and add it to the inventory.
 * Also includes a back button to return to the previous screen.
 *
 * The activity uses {@link ActivityAddInventoryBinding} for view binding and {@link PharmacyRepository}
 * for data operations. It handles user input, performs validation, and updates the inventory database.
 *
 * @author Saria Kabbour
 * @since 2024-08-08
 */
public class AddInventoryActivity extends AppCompatActivity {

    private ActivityAddInventoryBinding binding;
    private PharmacyRepository repository;

    /**
     * Called when the activity is first created.
     * Initializes the layout, sets up the repository, and configures the UI elements.
     * Sets up click listeners for the add and back buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = PharmacyRepository.getRepository(getApplication());

        EditText drugNameEditText = binding.drugNameEditText; // Assuming you have this EditText in your layout
        Button addButton = binding.addDrugButton; // Assuming you have this Button in your layout
        Button backButton = binding.backButton;

        addButton.setOnClickListener(v -> {
            String drugName = drugNameEditText.getText().toString().trim();

            if (drugName.isEmpty()) {
                Toast.makeText(this, "Please enter a drug name.", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: add validation check for invalid characters

            Drug newDrug = new Drug(drugName);
            repository.insertDrug(newDrug);

            Toast.makeText(this, "Drug added to inventory!", Toast.LENGTH_SHORT).show();

            // Clear EditText
            drugNameEditText.setText("");
        });

        backButton.setOnClickListener(v -> {
            // Close the current activity and go back to the previous one
            finish();
        });
    }
}
