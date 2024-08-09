package com.example.project02.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.R;


public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
    private final TextView prescriptionViewItem;
    private PrescriptionViewHolder(View prescriptionView){
        super(prescriptionView);
        prescriptionViewItem = prescriptionView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind (String text){
        prescriptionViewItem.setText(text);
    }

    static PrescriptionViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_recycler_item, parent, false);
        return new PrescriptionViewHolder(view);
    }
}
