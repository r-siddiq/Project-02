package com.example.project02.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.project02.Database.entities.Prescription;

public class PrescriptionAdapter extends ListAdapter<Prescription, PrescriptionViewHolder>{
    public PrescriptionAdapter(@NonNull DiffUtil.ItemCallback<Prescription> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return PrescriptionViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {
        Prescription current = getItem(position);
        holder.bind(current.toString());
    }

    public static class PrescriptionDiff extends DiffUtil.ItemCallback<Prescription>{
        @Override
        public boolean areItemsTheSame(@NonNull Prescription oldItem, @NonNull Prescription newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Prescription oldItem, @NonNull Prescription newItem) {
            return oldItem.equals(newItem);
        }
    }
}
