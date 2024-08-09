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

public class CheckInventoryActivity extends AppCompatActivity {

    private PharmacyRepository repository;
    private TextView inventoryTextView;
    private Button backButton;

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
