package com.example.project02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02.Database.PharmacyRepository;
import com.example.project02.Database.entities.Drug;
import com.example.project02.databinding.ActivityAddInventoryBinding;

public class AddInventoryActivity extends AppCompatActivity {

    private ActivityAddInventoryBinding binding;
    private PharmacyRepository repository;

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

            //clear editText
            drugNameEditText.setText("");
        });
        backButton.setOnClickListener(v -> {
            // Close the current activity and go back to the previous one
            finish();
        });
    }
}
