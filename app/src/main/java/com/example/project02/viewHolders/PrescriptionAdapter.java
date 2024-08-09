package com.example.project02.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.Database.entities.Prescription;
import com.example.project02.R;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    private List<Prescription> prescriptionList;

    public PrescriptionAdapter(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_prescription, parent, false); // Use item_prescription layout
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {
        Prescription prescription = prescriptionList.get(position);
        holder.prescriptionName.setText(prescription.getDrugName());

        // Combine fields to display in the details
        String details = "Quantity: " + prescription.getQuantity() +
                "\nRefills: " + prescription.getRefills() +
                "\nRx ID: " + prescription.getRxid() +
                "\nPatient ID: " + prescription.getPatientId();

        holder.prescriptionDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    static class PrescriptionViewHolder extends RecyclerView.ViewHolder {

        TextView prescriptionName;
        TextView prescriptionDetails;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            prescriptionName = itemView.findViewById(R.id.drugName); // Assuming you have this ID in item_prescription.xml
            prescriptionDetails = itemView.findViewById(R.id.prescriptionDetails); // Assuming you have this ID in item_prescription.xml
        }
    }
}