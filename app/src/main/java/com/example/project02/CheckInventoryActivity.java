package com.example.project02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.Drug;

import java.util.List;

/**
 * Activity to check and display the inventory of drugs.
 * Retrieves a list of drugs from the repository and shows them in a TextView.
 * Provides a back button to close the activity and return to the previous screen.
 *
 * The activity uses {@link PharmacyRepository} to access drug data and {@link LiveData}
 * to observe changes in the drug list. The inventory list is displayed in a {@link TextView}.
 *
 * @author Saria Kabbour
 * @since 2024-08-08
 */
public class CheckInventoryActivity extends AppCompatActivity {

    private PharmacyRepository repository;
    private TextView inventoryTextView;
    private Button backButton;

    /**
     * Called when the activity is first created.
     * Initializes the layout, sets up the repository, and configures the UI elements.
     * Fetches and displays the list of drugs from the repository.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory);

        repository = PharmacyRepository.getRepository(getApplication());

        inventoryTextView = findViewById(R.id.inventoryTextView);
        backButton = findViewById(R.id.backButton);

        // Fetch and display the inventory list
        LiveData<List<Drug>> drugsLiveData = repository.getAllDrugsList();
        drugsLiveData.observe(this, drugs -> {
            if (drugs != null && !drugs.isEmpty()) {
                StringBuilder inventoryList = new StringBuilder();
                for (Drug drug : drugs) {
                    inventoryList.append(drug.getName()).append("\n");
                }
                inventoryTextView.setText(inventoryList.toString());
            } else {
                inventoryTextView.setText("Inventory is empty.");
            }
        });

        // Set up the back button
        backButton.setOnClickListener(v -> finish());
    }
}
